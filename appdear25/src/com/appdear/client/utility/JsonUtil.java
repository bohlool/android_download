package com.appdear.client.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	/**
	 * ���ض���Object
	 * 
	 * @param json
	 * @param name
	 * @param defaultobj
	 * @return
	 */
	public static Object get(JSONObject json, String name, Object defaultobj) {
		if (json != null) {
			try {
				if (json.has(name) && json.get(name) != null) {
					return json.get(name);
				} else {
					return defaultobj;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return defaultobj;
			}
		}
		return defaultobj;
	}

	/**
	 * ����json����String
	 * 
	 * @param json
	 * @param name
	 * @param defaultobj
	 * @return
	 */
	public static String getString(JSONObject json, String name,
			String defaultobj) {
		if (json != null) {
			try {
				if (json.has(name) && json.getString(name) != null) {
					return json.getString(name);
				} else {
					return defaultobj;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return defaultobj;
			}
		}
		return defaultobj;
	}

	/**
	 * ����json����boolean
	 * 
	 * @param json
	 *            json����
	 * @param name
	 *            keyֵ
	 * @param defaultobj
	 *            Ĭ�Ϸ���ֵ
	 * @return
	 */
	public static boolean getBoolean(JSONObject json, String name,
			boolean defaultobj) {
		if (json != null) {
			try {
				if (json.has(name)) {
					return json.getBoolean(name);
				} else {
					return defaultobj;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return defaultobj;
			}
		}
		return defaultobj;
	}

	/**
	 * ����json����int
	 * 
	 * @param json
	 *            json����
	 * @param name
	 *            keyֵ
	 * @param defaultobj
	 *            Ĭ�Ϸ���ֵ
	 * @return
	 */
	public static int getInt(JSONObject json, String name, int defaultobj) {
		if (json != null) {
			try {
				if (json.has(name)) {
					return json.getInt(name);
				} else {
					return defaultobj;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return defaultobj;
			}
		}
		return defaultobj;
	}

	/**
	 * ����json����JSONObject
	 * 
	 * @param json
	 *            json����
	 * @param name
	 *            keyֵ
	 * @param defaultobj
	 *            Ĭ�Ϸ���ֵ
	 * @return
	 */
	public static JSONObject getJSONObject(JSONObject json, String name,
			JSONObject defaultobj) {
		if (json != null) {
			try {
				if (json.has(name)) {
					return json.getJSONObject(name);
				} else {
					return defaultobj;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return defaultobj;
			}
		}
		return defaultobj;
	}

	/**
	 * ���ַ�ѹ��
	 * 
	 * @param inputString
	 * @return
	 */
	public static byte[] deflaterStr(String inputString) {
		try {
			// Encode a String into bytes
			byte[] input = inputString.getBytes("UTF-8");

			// Compress the bytes
			byte[] output = new byte[inputString.getBytes().length];
			Deflater compresser = new Deflater();
			compresser.setInput(input);
			compresser.finish();
			int compressedDataLength = compresser.deflate(output);
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ֽڽ�ѹ
	 * 
	 * @param output
	 * @return
	 */
	public static String InflaterByte(byte[] output) {
		try {
			// Decompress the bytes
			Inflater decompresser = new Inflater();
			decompresser.setInput(output, 0, output.length);
			byte[] result = new byte[output.length];
			int resultLength = decompresser.inflate(result);
			decompresser.end();

			// Decode the bytes into a String
			String outputString = new String(result, 0, resultLength, "UTF-8");
			return outputString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ѹ��
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");
	}

	// ��ѹ��
	public static String uncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
				str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()ʹ��ƽ̨Ĭ�ϱ��룬Ҳ������ʽ��ָ����toString("GBK")
		return out.toString();
	}

	/**
	 * ѹ���ַ���Ϊ byte[] �������ʹ��new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)����
	 * ����Ϊ�ַ���
	 * 
	 * @param str
	 *            ѹ��ǰ���ı�
	 * @return
	 * @throws UnsupportedEncodingException
	 */
//	public static String newcompress(String str)
//			throws UnsupportedEncodingException {
//		if (str == null)
//			return null;
//
//		byte[] compressed;
//		ByteArrayOutputStream out = null;
//		ZipOutputStream zout = null;
//
//		try {
//			out = new ByteArrayOutputStream();
//			zout = new ZipOutputStream(out);
//			zout.putNextEntry(new ZipEntry("0"));
//			zout.write(str.getBytes());
//			zout.closeEntry();
//			compressed = out.toByteArray();
//		} catch (IOException e) {
//			compressed = null;
//		} finally {
//			if (zout != null) {
//				try {
//					zout.close();
//				} catch (IOException e) {
//				}
//			}
//			if (out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//
//		String ret = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
//		return ret;
//	}

	/**
	 * ��ѹ����� byte[] ���ݽ�ѹ��
	 * 
	 * @param compressed
	 *            ѹ����� byte[] ����
	 * @return ��ѹ����ַ���
	 */
//	public static String decompress(byte[] compressed) {
//		if (compressed == null)
//			return null;
//
//		ByteArrayOutputStream out = null;
//		ByteArrayInputStream in = null;
//		ZipInputStream zin = null;
//		String decompressed;
//		try {
//			out = new ByteArrayOutputStream();
//			in = new ByteArrayInputStream(compressed);
//			zin = new ZipInputStream(in);
//			ZipEntry entry = zin.getNextEntry();
//			byte[] buffer = new byte[1024];
//			int offset = -1;
//			while ((offset = zin.read(buffer)) != -1) {
//				out.write(buffer, 0, offset);
//			}
//			decompressed = out.toString();
//		} catch (IOException e) {
//			decompressed = null;
//		} finally {
//			if (zin != null) {
//				try {
//					zin.close();
//				} catch (IOException e) {
//				}
//			}
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//				}
//			}
//			if (out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//
//		return decompressed;
//	}

}
