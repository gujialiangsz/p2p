package com.biocome.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.unzip.UnzipUtil;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.biocome.base.exception.BusinessException;

public class FtpUtils {
	public static void downloadDir(FTPClient ftpClient, String ftpDir,
			String localDir) {
		localDir = StringUtils.lastAppendUnique(localDir + ftpDir, "/");
		FileOutputStream fos = null;
		try {
			File parentFile = new File(localDir);
			File tempFile = null;
			File destFile = null;
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			// 设置ftp目录
			String ftpRecvPath = ftpDir + "/";
			// 改变ftp当前目录
			ftpClient.changeWorkingDirectory(ftpRecvPath);
			// 获取ftp文件列表
			FTPFile[] ftpFiles = ftpClient.listFiles();
			ftpClient.setBufferSize(1024 * 1);
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			for (FTPFile ftpFile : ftpFiles) {
				String datFileName = null;
				System.out.println(ftpFile.getName());
				byte[] b = ftpFile.getName().getBytes("iso-8859-1");
				if (b[0] == -17 && b[1] == -69 && b[2] == -65)
					datFileName = new String(b, "UTF-8");
				else
					datFileName = new String(b, "GBK");
				// 筛选文件
				if (StringUtils.isMatch("[\\W\\d\\w]*[.]$", datFileName))
					continue;
				// 获取临时文件对象
				if (ftpFile.isDirectory()) {
					System.out.println("downs dir:" + ftpFile.getName()
							+ " to:" + localDir);
					downloadDir(ftpClient, datFileName, localDir);
				} else {
					System.out.println("downs file:" + ftpFile.getName()
							+ " to:" + localDir);
					tempFile = createFileForce(localDir + datFileName
							+ ".cache");
					// 获取目标文件对象
					destFile = createFileForce(localDir + datFileName);
					// 以字节流模式写入文件
					fos = new FileOutputStream(tempFile);
					ftpClient.retrieveFile(datFileName, fos);
					fos.close();
					// 改文件名
					tempFile.renameTo(destFile);
				}
			}
		} catch (Exception e) {
			disConnection(ftpClient);
			new BusinessException("下载文件失败");
		}
	}

	public static void downloadFile(FTPClient ftpClient, String ftpDir,
			List<String> fileNames, String localDir) {
		// 创建FTPClient对象
		FileOutputStream fos = null;
		try {
			File parentFile = new File(localDir);
			File tempFile = null;
			File destFile = null;
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			// 设置ftp目录
			String ftpRecvPath = ftpDir + "/";
			// 改变ftp当前目录
			ftpClient.changeWorkingDirectory(ftpRecvPath);
			// 获取ftp文件列表
			FTPFile[] ftpFiles = ftpClient.listFiles();
			ftpClient.setBufferSize(1024 * 1);
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			for (FTPFile ftpFile : ftpFiles) {
				String datFileName = null;
				byte[] b = ftpFile.getName().getBytes("iso-8859-1");
				if (b[0] == -17 && b[1] == -69 && b[2] == -65)
					datFileName = new String(b, "UTF-8");
				else
					datFileName = new String(b, "GBK");

				// 筛选文件

				if (fileNames.contains(datFileName)) {
					localDir = StringUtils.lastAppendUnique(localDir, "/");
					System.out.println("down file:" + ftpFile.getName()
							+ " to:" + localDir);
					if (ftpFile.isDirectory()) {
						downloadDir(ftpClient, datFileName, localDir);
					} else {
						// 获取临时文件对象
						tempFile = createFileForce(localDir + datFileName
								+ ".cache");
						// 获取目标文件对象
						destFile = createFileForce(localDir + datFileName);
						// 以字节流模式写入文件
						fos = new FileOutputStream(tempFile);
						ftpClient.retrieveFile(datFileName, fos);
						fos.close();
						// 改文件名
						tempFile.renameTo(destFile);
					}
				}
			}
		} catch (Exception e) {
			throw new BusinessException("下载文件失败", e);
		} finally {
			disConnection(ftpClient);
		}
	}

	public static List<String> listFileNames(FTPClient ftpClient,
			String ftpDir, String regex) {
		List<String> names = new ArrayList<String>();
		try {
			// 设置ftp目录
			String ftpRecvPath = ftpDir + "/";
			// 改变ftp当前目录
			ftpClient.changeWorkingDirectory(ftpRecvPath);
			// 获取ftp文件列表
			FTPFile[] ftpFiles = ftpClient.listFiles();
			ftpClient.setBufferSize(1024);
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			for (FTPFile ftpFile : ftpFiles) {
				String datFileName = null;
				byte[] b = ftpFile.getName().getBytes("iso-8859-1");
				if (b[0] == -17 && b[1] == -69 && b[2] == -65)
					datFileName = new String(b, "UTF-8");
				else
					datFileName = new String(b, "GBK");
				if (regex != null && !StringUtils.isMatch(regex, datFileName))
					continue;
				names.add(datFileName);
			}

		} catch (Exception e) {
			throw new BusinessException("获取文件列表失败", e);
		} finally {
			disConnection(ftpClient);
		}
		return names;
	}

	public static FTPClient getConnection(String ftpIp, String ftpUserName,
			String ftpPassWord, int port) {
		FTPClient ftpClient = new FTPClient();
		try {
			if (port == 0)
				ftpClient.connect(ftpIp);
			else
				ftpClient.connect(ftpIp, port);
			if (ftpClient.login(ftpUserName, ftpPassWord)) {
				ftpClient.enterLocalPassiveMode();
				return ftpClient;
			} else
				throw new BusinessException("连接失败");
		} catch (Exception e) {
			throw new BusinessException("连接失败", e);
		}

	}

	public static void disConnection(FTPClient ftpClient) {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
			}

		}
	}

	/**
	 * 强制创建文件，存在则删除
	 * 
	 * @param name
	 * @return
	 */
	public static File createFileForce(String name) {
		File file = new File(name);
		if (file.exists()) {
			file.delete();
		}
		return file;
	}

	public static void uploadFile(String ftpIp, String ftpUserName,
			String ftpPassWord, int port, String localDir, String ftpDir) {
		uploadFile(getConnection(ftpIp, ftpUserName, ftpPassWord, port),
				localDir, ftpDir);
	}

	public static void uploadFile(FTPClient ftpClient, String localDir,
			String ftpDir) {
		BufferedInputStream fis = null;
		File newFile = null;
		File oldFile = null;
		try {
			newFile = new File(localDir + ".tmp");
			oldFile = new File(localDir);
			fis = new BufferedInputStream(new FileInputStream(localDir));
			ftpClient.mkd(ftpDir);
			ftpClient.changeWorkingDirectory(ftpDir);
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

			if (ftpClient.storeFile(newFile.getName(), fis)) {
				ftpClient.rename(newFile.getName(), oldFile.getName());
				ftpClient.deleteFile(newFile.getName());
			} else {
				System.out.println("upload file error");
			}

		} catch (Exception e) {
			throw new BusinessException("上传文件失败");
			// try {
			// InputStream is = ftpClient.retrieveFileStream(newFile
			// .getName());// 获取远程ftp上指定文件的InputStream
			// if (null != is) {
			// ftpClient.deleteFile(newFile.getName());
			// }
			// } catch (Exception e1) {
			// }
		} finally {
			disConnection(ftpClient);
		}
	}

	/**
	 * 解压加密文件
	 * 
	 * @param sourceFile
	 * @param destinationPath
	 * @param pwd
	 */
	public static void unzipEncryptFile(String sourceFile,
			String destinationPath, String pwd) {
		ZipInputStream is = null;
		BufferedOutputStream os = null;

		try {
			ZipFile zipFile = new ZipFile(sourceFile);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(pwd);
			}
			List<?> fileHeaderList = zipFile.getFileHeaders();
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
				if (fileHeader != null) {
					String outFilePath = destinationPath
							+ System.getProperty("file.separator")
							+ fileHeader.getFileName();
					File outFile = new File(outFilePath);
					if (fileHeader.isDirectory()) {
						outFile.mkdirs();
						continue;
					}
					File parentDir = outFile.getParentFile();
					if (!parentDir.exists()) {
						parentDir.mkdirs();
					}
					is = zipFile.getInputStream(fileHeader);
					os = new BufferedOutputStream(new FileOutputStream(outFile));

					int readLen = -1;
					byte[] buff = new byte[2048];
					while ((readLen = is.read(buff)) != -1) {
						os.write(buff, 0, readLen);
					}
					UnzipUtil.applyFileAttributes(fileHeader, outFile);
					System.out.println("Done extracting: "
							+ fileHeader.getFileName());
				} else {
					System.err.println("fileheader is null. Shouldn't be here");
				}
			}
		} catch (Exception e) {
			throw new BusinessException("解压缩失败");
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 加密压缩文件
	 * 
	 * @param files
	 * @param destinationPath
	 * @param pwd
	 */
	public static void zipEncryptFile(String destinationPath, String pwd,
			String... files) {
		ZipOutputStream outputStream = null;
		BufferedInputStream inputStream = null;

		try {
			outputStream = new ZipOutputStream(new FileOutputStream(new File(
					destinationPath)));
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			if (pwd != null) {
				parameters.setEncryptFiles(true);
				parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
				parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
				parameters.setPassword(pwd);
			}
			for (String fs : files) {
				File file = new File(fs);
				outputStream.putNextEntry(file, parameters);
				if (file.isDirectory()) {
					outputStream.closeEntry();
					continue;
				}
				inputStream = new BufferedInputStream(new FileInputStream(file));
				byte[] readBuff = new byte[4096];
				int readLen = -1;
				while ((readLen = inputStream.read(readBuff)) != -1) {
					outputStream.write(readBuff, 0, readLen);
				}
				outputStream.closeEntry();
				inputStream.close();
			}
			outputStream.finish();

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("加密压缩失败");
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 加密压缩文件
	 * 
	 * @param files
	 *            包含文件和文件夹
	 * @param destinationPath
	 * @param pwd
	 */
	public static void zipEncryptFiles(String destinationPath, String pwd,
			String... files) {
		try {
			ZipFile zipFile = new ZipFile(new File(destinationPath));
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			if (pwd != null) {
				parameters.setEncryptFiles(true);
				parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
				parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
				parameters.setPassword(pwd);
			}
			for (String fs : files) {
				File file = new File(fs);
				if (file.isDirectory()) {
					zipFile.addFolder(file, parameters);
				} else {
					zipFile.addFile(file, parameters);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("加密压缩失败");
		}
	}
}
