package com.organizer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * Transfer user defined number of bytes between two streams.
 *
 * @author Evgeni Stefanov
 *
 */
public class TransferObject implements AutoCloseable {

	private static final Logger LOGGER = Logger.getLogger(TransferObject.class
			.getName());

	private InputStream ist = null;
	private OutputStream ost = null;

	public TransferObject() {

	}

	public TransferObject(InputStream ist, OutputStream ost) {
		this.ist = ist;
		this.ost = ost;
	}

	@Override
	public void close() throws IOException {
		if (ist != null) {
			ist.close();
			ist = null;
		}
		if (ost != null) {
			ost.close();
			ost = null;
		}
	}

	/**
	 * Transfer bytes between two streams.
	 *
	 * @param numberOfBytes
	 * @return
	 * @throws IOException
	 */
	public int transfer(int numberOfBytes) throws IOException {
		int transfered;
		byte[] buff = new byte[numberOfBytes];
		transfered = ist.read(buff, 0, numberOfBytes);
		if (transfered != -1) {
			ost.write(buff, 0, transfered);
		}
		return transfered;
	}

	/**
	 * Transfer data between two streams.
	 *
	 * @param numberOfBytes
	 *            user defined number of bytes. Thath will be transfered.
	 * @param offset
	 *            number of bytes that will be missed.
	 * @return number of transfered bytes.
	 * @throws IOException
	 */
	public int transfer(int numberOfBytes, int offset) throws IOException {
		int transfered;
		ist.skip(offset);
		byte[] buff = new byte[numberOfBytes];
		transfered = ist.read(buff, 0, numberOfBytes);
		ost.write(buff, 0, transfered);
		return transfered;
	}

	public void transferAllBytes(InputStream inputStream,
			OutputStream outputStream, int bufferSize) throws IOException {
		this.ist = inputStream;
		this.ost = outputStream;
		while (transfer(bufferSize) != -1) {

		}
	}
}
