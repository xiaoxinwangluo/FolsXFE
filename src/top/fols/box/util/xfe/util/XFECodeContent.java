package top.fols.box.util.xfe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import top.fols.box.io.interfaces.XInterfaceReleaseBufferable;
import top.fols.box.io.os.XFile;
import top.fols.box.util.XByteEncode;
import top.fols.box.util.xfe.lang.keywords.XFEKeyWords;

public abstract class XFECodeContent implements XInterfaceReleaseBufferable {
	public static final String separator = File.separator;
	public static final char separatorChar = File.separatorChar;
	public static final String extensionNameSeparatorChar = XFEKeyWords.CODE_FILE_EXTENSION_NAME_SEPARATOR;

	private String path;
	private String extensionName;
	private String className;
	private String fileName;
	private Charset charset;
	private boolean isRelease;
	public XFECodeContent(String path, Charset charset) throws NullPointerException {
        if (null == path) {
            throw new NullPointerException("path");
        }
		this.path = XFile.formatPath(path, separatorChar);
		this.extensionName = XFile.getExtensionName(this.path, separator, extensionNameSeparatorChar);
		this.className = XFile.getNameNoExtension(this.path, separator, extensionNameSeparatorChar);
		this.fileName = XFile.getName(this.path, separator);
		this.charset = charset;
	}
	public String getPath() {
		return this.path;
	}

	public Charset getCharset() {
		return this.charset;
	}

	public String getClassName() {
		return this.className;
	}
	public String getFileName() {
		return this.fileName;
	}
	public String getFileExtensionName() {
        return this.extensionName;
	}


	//inherit
	public abstract char[] getContent() throws IOException,OutOfMemoryError,RuntimeException;
	//inherit
	@Override
	public abstract void releaseBuffer();


	private static char[] readFileContent(String path, Charset charset) throws IOException,OutOfMemoryError,RuntimeException {
		byte[] bytes = XFECodeContent.readFileBytes(path);
		return XFECodeContent.bytesToChars(bytes, charset);
	}
	private static byte[] readFileBytes(String path) throws IOException,OutOfMemoryError,RuntimeException {
		File file;
		FileInputStream fis = null;
		byte[] bytes = null;
		long length = 0;
		int read = -1;
		try {
			file = new File(path);
			fis = new FileInputStream(file);
			if ((length = file.length()) > Integer.MAX_VALUE) {
				throw new OutOfMemoryError("filesize=" + length);
			}
			if ((read = fis.read(bytes = new byte[(int)length])) != length) {
				throw new RuntimeException(String.format("read file error, expected read length=%s, actual read length=%s", length, read));
			}
			return bytes;
		} catch (IOException e) {
			throw e;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e = null;
				}
			}
		}
	}
	private static char[] readZipFileContent(ZipFile zf, String path, Charset charset) throws IOException,OutOfMemoryError,RuntimeException {
		byte[] bytes = XFECodeContent.readZipFileBytes(zf, path);
		return XFECodeContent.bytesToChars(bytes, charset);
	}
	private static byte[] readZipFileBytes(ZipFile zf, String path) throws IOException,OutOfMemoryError,RuntimeException {
		ZipEntry ze = zf.getEntry(path);
		InputStream fis = XFECodeContent.getZipEntryInputStream(zf, ze);
		if (fis == null) {
			return null;
		}
		long length;
		int read;
		byte[] bytes;
		if ((length = ze.getSize()) > Integer.MAX_VALUE) {
			throw new OutOfMemoryError("filesize=" + length);
		}
		if ((read = fis.read(bytes = new byte[(int)length])) != length) {
			throw new RuntimeException(String.format("read file error, expected read length=%s, actual read length=%s", length, read));
		}
		fis.close();
		return bytes;
	}





	public static InputStream getZipEntryInputStream(ZipFile zf, ZipEntry ze) {
		try {
			InputStream zis = zf.getInputStream(ze);
			return zis;
		} catch (Throwable e) {
			return null;
		}
	}




	/**
	 *
	 * @param charset isnull use CODE_DEFAULT_CHARSET
	 */
	private static char[] bytesToChars(byte[] bytes, Charset charset) {
        if (null == charset) { charset = XFEKeyWords.CODE_DEFAULT_CHARSET_UTF_8; }
        char[] chars = XByteEncode.bytesToChars(bytes, 0, bytes.length, charset);
        return chars;
	}






	public static XFECodeContent wrapZipFile(final File zipfile, ZipFile zipfileobject, 
											 final String path, final Charset charset) {
		return new XFEZipFileContent(zipfile, zipfileobject, 
			path, charset);
	}
	public static class XFEZipFileContent extends XFECodeContent {
		private char[] content = null;
		private File zipfile;
		private ZipFile zipfile2;
		public XFEZipFileContent(File zipfile, ZipFile zipfile2, 
								 String path, Charset charset) {
			super(path, charset);
			this.zipfile = zipfile;
			this.zipfile2 = zipfile2;
		}

		public File getZipFile() {
			return this.zipfile;
		}

		@Override
		public void releaseBuffer() {
			// TODO: Implement this method
			if (super.isRelease) {
				return;
			}
			super.isRelease = true;
			this.content = null;
		}

		@Override
		public char[] getContent() throws IOException, OutOfMemoryError, RuntimeException {
			// TODO: Implement this method
			if (super.isRelease) {
				throw new RuntimeException("already release");
			}
			char[] content = null == this.content ?this.content = XFECodeContent.readZipFileContent(this.zipfile2, super.path, super.getCharset()): this.content;
			return content;
		}
	}



	public static XFECodeContent wrapFile(final File fileObject, final Charset charset) {
		return new XFECodeContent(fileObject.getAbsolutePath(), charset){
			private File file = fileObject;
			private char[] content = null;

			@Override
			public void releaseBuffer() {
				// TODO: Implement this method
				if (super.isRelease) {
					return;
				}
				super.isRelease = true;
				this.content = null;
			}

			@Override
			public char[] getContent() throws IOException, OutOfMemoryError, RuntimeException {
				if (super.isRelease) {
					throw new RuntimeException("already release");
				}
				return null == this.content ?this.content = XFECodeContent.readFileContent(super.path, super.getCharset()): this.content;
			}
		};
	}
	public static XFECodeContent wrapBytes(final String filePath, final byte[] fileContent, final Charset charset) {
		return new XFECodeContent(filePath, charset) {
			private byte[] bytes = fileContent;
			private char[] content = null;

			@Override
			public void releaseBuffer() {
				// TODO: Implement this method
				if (super.isRelease) {
					return;
				}
				super.isRelease = true;
				this.content = null;
				this.bytes = null;
			}

			@Override
			public char[] getContent() {
				if (super.isRelease) {
					throw new RuntimeException("already release");
				}
				if (null == this.content) {
					if (null != this.bytes) {
						this.content = bytesToChars(this.bytes, this.getCharset());
						this.bytes = null;
					}
				}
				return this.content;
			}
		};
	}
	
	public static XFECodeContent wrapString(final String filePath, final String fileContent) {
        return new XFECodeContent(filePath, Charset.defaultCharset()){
            private char[] content = fileContent.toCharArray();

            @Override
            public void releaseBuffer() {
                // TODO: Implement this method
                if (super.isRelease) {
                    return;
                }
                super.isRelease = true;
                this.content = null;
            }

            @Override
            public char[] getContent() {
                if (super.isRelease) {
                    throw new RuntimeException("already release");
                }
                return content;
            }
        };
	}
	public static XFECodeContent wrapChars(final String filePath, final char[] fileContent) {
		return new XFECodeContent(filePath, Charset.defaultCharset()){
			private char[] content = fileContent;

			@Override
			public void releaseBuffer() {
				// TODO: Implement this method
				if (super.isRelease) {
					return;
				}
				super.isRelease = true;
				this.content = null;
			}

			@Override
			public char[] getContent() {
				if (super.isRelease) {
					throw new RuntimeException("already release");
				}
				return content;
			}
		};
	}
	
	
	
	




} 
