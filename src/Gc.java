import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import top.fols.box.lang.reflect.optdeclared.XReflectAccessible;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XDoubleLinked;

/**
 * Useless
 */
public class Gc {
	private DataDeal<Object> pt = new DataDeal<Object>(3000).setDataDeal(new GcObjectField<Object>());

	public void remove(Object object) {
		this.pt.post(object);
	}

	public static class GcObjectField<T extends Object> implements DataDealInterface<T> {
		private Map<Class, Field[]> fieldCache = new HashMap<>();

		private Field[] getFields(Class cls) {
			if (null == cls) {
				return XStaticFixedValue.nullFieldArray;
			}
			Field[] cache = null;
			if (null == (cache = this.fieldCache.get(cls))) {
				this.fieldCache.put(cls, cache = XReflectAccessible.getFieldsAllSetAccessible(cls));
			}
			return cache;
		}

		@Override
		public void deal(Object data) {
			// TODO: Implement this method
			if (null == data) {
				return;
			}
			Class cls = data.getClass();
			Field[] fs = this.getFields(cls);
			int fm;
			for (Field f : fs) {
				fm = f.getModifiers();
				if (Modifier.isFinal(fm) || Modifier.isStatic(fm)) {
					continue;
				}
				try {
					f.set(data, null);
					System.out.println("remove: " + cls.getCanonicalName() + "." + f.getName());
				} catch (Throwable e) {
					continue;
				}
			}
			data = null;
		}
	}

	public static abstract interface DataDealInterface<T extends Object> {
		public abstract void deal(T data);
	}

	public static class DataDeal<T extends Object> {
		private long nullDataCheckTime = 0;// 超过这个时间将会结束线程
		private long threadSleepTime = 100;

		private DataDealInterface<T> dd = null;
		private DataDealThread<T> dt = null;
		private AtomicBoolean ts = new AtomicBoolean(false);
		private XDoubleLinked.VarLinked<T> linked = new XDoubleLinked.VarLinked<>(null);

		public DataDeal(long nullSleepTime) {
			this.nullDataCheckTime = nullSleepTime;
		}

		public DataDeal<T> setDataDeal(DataDealInterface<T> dealer) {
			this.dd = dealer;
			return this;
		}

		public void post(T object) {
			XDoubleLinked.VarLinked<T> linked = new XDoubleLinked.VarLinked<T>(object);
			this.linked.addNext(linked);
			this.startThread();
		}

		private void startThread() {
			if (!this.ts.get()) {
				this.ts.set(true);
				this.dt = new DataDealThread<T>(this);
				this.dt.start();
			}
		}
	}

	// automatic death thread
	private static class DataDealThread<T> extends Thread {
		private DataDeal superObject;

		public DataDealThread(DataDeal superObject) {
			this.superObject = superObject;
		}

		@Override
		public void run() {
			// TODO: Implement this method
			try {
				XDoubleLinked.VarLinked<T> linked = this.superObject.linked;
				one: while (true) {
					XDoubleLinked.VarLinked<T> now = (XDoubleLinked.VarLinked<T>) linked.getNext();
					if (null == now) {
						// no data need deal;
						long time = System.currentTimeMillis();
						two: while (true) {
							this.sleep(this.superObject.threadSleepTime);
							long nowtime = System.currentTimeMillis();
							if (linked.hasNext()) {
								continue one;
							} else {
								if (nowtime - time > this.superObject.nullDataCheckTime) {
									this.superObject.ts.set(false);
									break one;
								}
							}
						}
					} else {
						try {
							this.superObject.dd.deal(now.content());
						} catch (Throwable e) {
							e.printStackTrace();
						}
						this.superObject.linked.remove(now);
						now = null;
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
