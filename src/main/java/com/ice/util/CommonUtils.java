package com.ice.util;

import java.text.DecimalFormat;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility class providing common conversion methods for bytes, hexadecimal,
 * binary and other formats.
 * This class contains various static methods for data type conversion and
 * formatting.
 * 
 * @author: eclair
 * @date: 2020/8/20 17:07
 * @description: Collection of utility methods for common data conversion
 *               operations
 */
public class CommonUtils {
	/** Hexadecimal character set used for conversions */
	static final String HEX = "0123456789abcdef";

	/**
	 * Converts a byte array to a hexadecimal string with "0x" prefix and spaces
	 * between bytes.
	 * 
	 * Example:
	 * byte[] data = {0x12, 0x34, 0x56};
	 * String result = convertByteToSignWhiteHex(data);
	 * // result = "0x12 0x34 0x56 "
	 * 
	 * @param bytes The byte array to convert
	 * @return A string of space-separated hexadecimal values with "0x" prefix
	 */
	public static String convertByteToSignWhiteHex(byte[] bytes) {
		StringBuilder dataSB = new StringBuilder();
		for (byte datum : bytes) {
			dataSB.append(String.format("0x%02x", datum)).append(" ");
		}
		return dataSB.toString();
	}

	/**
	 * Converts a byte array to a hexadecimal string without "0x" prefix or spaces.
	 * 
	 * Example:
	 * byte[] data = {0x12, 0x34, 0x56};
	 * String result = convertByteToSignHex(data);
	 * // result = "123456"
	 * 
	 * @param bytes The byte array to convert
	 * @return A string of concatenated hexadecimal values without prefix
	 */
	public static String convertByteToSignHex(byte[] bytes) {
		StringBuilder dataSB = new StringBuilder();
		for (byte datum : bytes) {
			dataSB.append(String.format("%02x", datum));
		}
		return dataSB.toString();
	}

	/**
	 * Converts a byte array to a hexadecimal string without separators.
	 * This is a convenience method that calls bytes2hex(bytes, "").
	 * 
	 * Example:
	 * byte[] data = {0x12, 0x34, 0x56};
	 * String result = bytes2hex(data);
	 * // result = "123456"
	 * 
	 * @param bytes The byte array to convert
	 * @return A hexadecimal string representation
	 */
	public static String bytes2hex(byte[] bytes) {
		return bytes2hex(bytes, "");
	}

	/**
	 * Converts a byte array to a hexadecimal string with a specified separator.
	 * 
	 * Example:
	 * byte[] data = {0x12, 0x34, 0x56};
	 * String result = bytes2hex(data, "-");
	 * // result = "12-34-56"
	 * 
	 * @param bytes The byte array to convert
	 * @param split The separator to insert between each byte's hex representation
	 * @return A hexadecimal string with the specified separator
	 */
	public static String bytes2hex(byte[] bytes, String split) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			// Get the high 4 bits of the byte, AND with 0x0f to get a value 0-15, convert
			// to hex
			sb.append(HEX.charAt((b >> 4) & 0x0f));
			// Get the low 4 bits of the byte, AND with 0x0f to get a value 0-15, convert to
			// hex
			sb.append(HEX.charAt(b & 0x0f));
			if (!StringUtils.isEmpty(split)) {
				sb.append(split);
			}
		}

		String result = sb.toString();
		if (!StringUtils.isEmpty(split)) {
			result = result.substring(0, result.length() - split.length());
		}

		return result;
	}

	/**
	 * Converts a byte array to a hexadecimal string after reversing the byte order.
	 * 
	 * Example:
	 * byte[] data = {0x12, 0x34, 0x56};
	 * String result = bytes2hexRevert(data);
	 * // result = "563412"
	 * 
	 * @param bytes The byte array to convert
	 * @return A hexadecimal string representation with reversed byte order
	 */
	public static String bytes2hexRevert(byte[] bytes) {
		// Reverse the byte array
		for (int i = 0; i < bytes.length / 2; i++) {
			byte temp = bytes[i];
			bytes[i] = bytes[bytes.length - 1 - i];
			bytes[bytes.length - 1 - i] = temp;
		}

		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			// Get the high 4 bits of the byte, AND with 0x0f to get a value 0-15, convert
			// to hex
			sb.append(HEX.charAt((b >> 4) & 0x0f));
			// Get the low 4 bits of the byte, AND with 0x0f to get a value 0-15, convert to
			// hex
			sb.append(HEX.charAt(b & 0x0f));
		}

		return sb.toString();
	}

	/**
	 * Converts a decimal number to its binary string representation with a
	 * specified length.
	 * 
	 * Example:
	 * int num = 10;
	 * String result = convertToBinary(num, 8);
	 * // result = "00001010"
	 * 
	 * @param num  The decimal number to convert
	 * @param size The number of bits to display in the result
	 * @return A binary string of the specified length
	 */
	public static String convertToBinary(int num, int size) {
		char[] chs = new char[size];
		for (int i = 0; i < size; i++) {
			chs[size - 1 - i] = (char) ((num >> i & 1) + '0');
		}
		return new String(chs);
	}

	/**
	 * Converts an integer to a 2-byte array (16 bits).
	 * 
	 * Example:
	 * int num = 4660; // 0x1234
	 * byte[] result = convertIntToByteForTwoBytes(num);
	 * // result = {0x12, 0x34}
	 * 
	 * @param num The integer to convert
	 * @return A 2-byte array representing the integer
	 */
	public static byte[] convertIntToByteForTwoBytes(int num) {
		byte[] result = new byte[2];
		result[0] = (byte) ((num >> 8) & 0xFF); // High byte
		result[1] = (byte) ((num) & 0xFF); // Low byte
		return result;
	}

	/**
	 * Converts an integer to a 4-byte array (32 bits).
	 * 
	 * Example:
	 * int num = 305419896; // 0x12345678
	 * byte[] result = convertIntToBytes(num);
	 * // result = {0x12, 0x34, 0x56, 0x78}
	 * 
	 * @param num The integer to convert
	 * @return A 4-byte array representing the integer
	 */
	public static byte[] convertIntToBytes(int num) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (num >> 24); // Highest byte
		bytes[1] = (byte) (num >> 16); // Second highest byte
		bytes[2] = (byte) (num >> 8); // Second lowest byte
		bytes[3] = (byte) num; // Lowest byte
		return bytes;
	}

	/**
	 * Converts an integer to a hexadecimal string with a specified separator.
	 * 
	 * Example:
	 * int num = 305419896; // 0x12345678
	 * String result = convertIntToHex(num, "-");
	 * // result = "12-34-56-78"
	 * 
	 * @param num   The integer to convert
	 * @param split The separator to insert between each byte's hex representation
	 * @return A hexadecimal string with the specified separator
	 */
	public static String convertIntToHex(int num, String split) {
		return bytes2hex(convertIntToBytes(num), split);
	}

	/**
	 * Converts an integer to a hexadecimal string with spaces as separators.
	 * This is a convenience method that calls convertIntToHex(num, " ").
	 * 
	 * Example:
	 * int num = 305419896; // 0x12345678
	 * String result = convertIntToHex(num);
	 * // result = "12 34 56 78"
	 * 
	 * @param num The integer to convert
	 * @return A hexadecimal string with spaces as separators
	 */
	public static String convertIntToHex(int num) {
		return convertIntToHex(num, " ");
	}

	/**
	 * Converts a 2-byte array to an integer.
	 * 
	 * Example:
	 * byte[] bytes = {0x12, 0x34};
	 * int result = byteArrayToInt(bytes);
	 * // result = 4660 (0x1234)
	 * 
	 * @param bytes The byte array to convert (at least 2 bytes)
	 * @return The integer value
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// From high to low bytes
		for (int i = 0; i < 2; i++) {
			int shift = (2 - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift; // Shift to higher position
		}
		return value;
	}

	/**
	 * Converts a byte to an unsigned integer (0-255).
	 * 
	 * Example:
	 * byte b = -1; // 0xFF in two's complement
	 * int result = byteToInt(b);
	 * // result = 255
	 * 
	 * @param b The byte to convert
	 * @return The unsigned integer value (0-255)
	 */
	public static int byteToInt(byte b) {
		return b & 0xFF;
	}

	/**
	 * Converts an integer to a byte, discarding all but the lowest 8 bits.
	 * 
	 * Example:
	 * int num = 257; // 0x101
	 * byte result = intToByte(num);
	 * // result = 1 (0x01)
	 * 
	 * @param num The integer to convert
	 * @return The byte value (lowest 8 bits of the integer)
	 */
	public static byte intToByte(int num) {
		return (byte) (num & 0xFF);
	}

	/**
	 * Converts an integer to an 8-bit binary string.
	 * 
	 * Example:
	 * int num = 170; // 0xAA (10101010 in binary)
	 * String result = convertToBinaryString(num);
	 * // result = "10101010"
	 * 
	 * @param num The integer to convert
	 * @return An 8-bit binary string representation
	 */
	public static String convertToBinaryString(int num) {
		char[] chs = new char[8];
		for (int i = 0; i < 8; i++) {
			chs[8 - 1 - i] = (char) (((num >> i) & 1) + '0');
		}
		return new String(chs);
	}

	/**
	 * Converts an integer to a reversed 8-bit binary string.
	 * 
	 * Example:
	 * int num = 170; // 0xAA (10101010 in binary)
	 * String result = convertToReversedBinaryString(num);
	 * // result = "01010101" (bits are reversed)
	 * 
	 * @param num The integer to convert
	 * @return A reversed 8-bit binary string representation
	 */
	public static String convertToReversedBinaryString(int num) {
		StringBuilder binaryString = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			binaryString.insert(0, (num >> i) & 1);
		}
		return binaryString.toString();
	}

	/**
	 * Converts a hexadecimal string to a double value by interpreting it as an IEEE
	 * 754 floating-point number.
	 * 
	 * Example:
	 * String hex = "41200000"; // IEEE 754 representation of 10.0
	 * Double result = hexToDouble(hex);
	 * // result = 10.0
	 * 
	 * @param t The hexadecimal string to convert
	 * @return The double value
	 */
	public static Double hexToDouble(String t) {
		if (StringUtils.isEmpty(t)) {
			return 0.0;
		}

		Long number = Long.parseLong(t, 16);

		if ("00000000".equals(t)) {
			return 0.0;
		}
		if ((t.length() > 8)) {
			return 0.0;
		}

		return (double) Float.intBitsToFloat(number.intValue());
	}

	/**
	 * Converts a hexadecimal string to a float value by interpreting it as an IEEE
	 * 754 floating-point number.
	 * 
	 * Example:
	 * String hex = "41200000"; // IEEE 754 representation of 10.0f
	 * Float result = hexToFloat(hex);
	 * // result = 10.0f
	 * 
	 * @param t The hexadecimal string to convert
	 * @return The float value
	 */
	public static Float hexToFloat(String t) {
		if (StringUtils.isEmpty(t)) {
			return 0.0F;
		}

		long number = Long.parseLong(t, 16);

		if ("00000000".equals(t)) {
			return 0.0F;
		}
		if ((t.length() > 8)) {
			return 0.0F;
		}

		return Float.intBitsToFloat((int) number);
	}

	/**
	 * Fills a string with a specified character to reach a desired length.
	 * The fill can be added to the beginning (left) or end (right) of the string.
	 * 
	 * Example:
	 * String str = "123";
	 * String leftPadded = fillString(str, "0", 5, true);
	 * // leftPadded = "00123"
	 * String rightPadded = fillString(str, "0", 5, false);
	 * // rightPadded = "12300"
	 * 
	 * @param t       The original string
	 * @param fillStr The character to fill with (must be a single character)
	 * @param length  The desired final length
	 * @param flag    If true, pad at beginning (left); if false, pad at end (right)
	 * @return The padded string
	 */
	public static String fillString(String t, String fillStr, int length, boolean flag) {
		if (StringUtils.isEmpty(t) || fillStr.length() != 1 || (length <= t.length())) {
			return t;
		}

		int strLength = t.length();
		StringBuilder tBuilder = new StringBuilder(t);
		for (int i = 0; i < length - strLength; i++) {
			if (flag) {
				tBuilder.insert(0, fillStr);
			} else {
				tBuilder.append(fillStr);
			}
		}
		t = tBuilder.toString();
		return t;
	}

	/**
	 * Extracts a 4-byte (32-bit) integer from a byte array starting at a specific
	 * position.
	 * 
	 * Example:
	 * byte[] data = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
	 * int result = getInt32(data, 1);
	 * // result = 0x02030405
	 * 
	 * @param input The byte array to extract from
	 * @param pos   The starting position
	 * @return The extracted 32-bit integer
	 */
	public static int getInt32(byte[] input, int pos) {
		return toInt(subBytes(input, pos, 4));
	}

	/**
	 * Extracts a 2-byte (16-bit) integer from a byte array starting at a specific
	 * position.
	 * 
	 * Example:
	 * byte[] data = {0x01, 0x02, 0x03, 0x04};
	 * int result = getInt16(data, 1);
	 * // result = 0x0203
	 * 
	 * @param input The byte array to extract from
	 * @param pos   The starting position
	 * @return The extracted 16-bit integer
	 */
	public static int getInt16(byte[] input, int pos) {
		return toInt(subBytes(input, pos, 2));
	}

	/**
	 * Converts a byte array to an integer by interpreting it as a hexadecimal
	 * number.
	 * 
	 * Example:
	 * byte[] data = {0x12, 0x34};
	 * int result = toInt(data);
	 * // result = 0x1234 (4660 in decimal)
	 * 
	 * @param input The byte array to convert
	 * @return The integer value
	 */
	public static int toInt(byte[] input) {
		String hex = bytes2hex(input, null);
		try {
			return Integer.parseInt(hex, 16);
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * Extracts a sub-array from a byte array.
	 * 
	 * Example:
	 * byte[] data = {0x01, 0x02, 0x03, 0x04, 0x05};
	 * byte[] result = subBytes(data, 1, 3);
	 * // result = {0x02, 0x03, 0x04}
	 * 
	 * @param input  The source byte array
	 * @param start  The starting index
	 * @param length The number of bytes to extract
	 * @return The sub-array
	 */
	public static byte[] subBytes(byte[] input, int start, int length) {
		if ((length > 0) && (input.length >= start + length)) {
			byte[] result = new byte[length];
			for (int i = 0; i < length; i++) {
				result[i] = input[i + start];
			}
			return result;
		}
		return new byte[0];
	}

	/**
	 * Converts a 32-bit integer to a 4-byte array.
	 * 
	 * Example:
	 * int value = 0x12345678;
	 * byte[] result = fromInt32(value);
	 * // result = {0x12, 0x34, 0x56, 0x78}
	 * 
	 * @param input The 32-bit integer to convert
	 * @return A 4-byte array
	 */
	public static byte[] fromInt32(int input) {
		byte[] result = new byte[4];
		result[0] = (byte) (input >> 24 & 0xFF); // Highest byte
		result[1] = (byte) (input >> 16 & 0xFF); // Second highest byte
		result[2] = (byte) (input >> 8 & 0xFF); // Second lowest byte
		result[3] = (byte) (input & 0xFF); // Lowest byte
		return result;
	}

	/**
	 * Converts a 16-bit integer to a 2-byte array.
	 * 
	 * Example:
	 * int value = 0x1234;
	 * byte[] result = fromInt16(value);
	 * // result = {0x12, 0x34}
	 * 
	 * @param input The 16-bit integer to convert
	 * @return A 2-byte array
	 */
	public static byte[] fromInt16(int input) {
		byte[] result = new byte[2];
		result[0] = (byte) (input >> 8 & 0xFF); // High byte
		result[1] = (byte) (input & 0xFF); // Low byte
		return result;
	}

	/**
	 * Formats a double value to have two decimal places.
	 * 
	 * Example:
	 * double value = 12.3456;
	 * double result = formatDouble(value);
	 * // result = 12.35 (rounded to two decimal places)
	 * 
	 * @param val The double value to format
	 * @return The formatted double value with two decimal places
	 */
	public static double formatDouble(double val) {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		double result = Double.valueOf(decimalFormat.format(val));
		return result;
	}

	public static float hexToFloat2(String hexString) {
		// 移除"0x"前缀（如果有）
		if (hexString.startsWith("0x")) {
			hexString = hexString.substring(2);
		}

		// 将十六进制字符串转换为整数
		long intBits = Long.parseLong(hexString, 16);

		// 使用Float.intBitsToFloat将位模式转换为浮点数
		return Float.intBitsToFloat((int) intBits);
	}

	public static void main(String[] args) {
		// 测试样例
		byte[] hexValues = { 0x42, (byte) 0x99, (byte) 0x0F, (byte) 0x5C };

		double result = hexToDouble(bytes2hex(hexValues));
		float result2 = hexToFloat2(bytes2hex(hexValues));

		System.out.println("十六进制值: 0x" + bytes2hex(hexValues) + " 转换为浮点数: " + result);
		System.out.println("十六进制值: 0x" + bytes2hex(hexValues) + " 转换为浮点数: " + result2);

	}
}