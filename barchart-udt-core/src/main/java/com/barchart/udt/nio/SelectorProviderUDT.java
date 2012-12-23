/**
 * Copyright (C) 2009-2012 Barchart, Inc. <http://www.barchart.com/>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.barchart.udt.nio;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.spi.SelectorProvider;

import com.barchart.udt.SocketUDT;
import com.barchart.udt.TypeUDT;

/**
 * selection provider for UDT
 * <p>
 * note: you must use the same system-wide provider instance for the same
 * {@link TypeUDT} of UDT channels and UDT selectors;
 */
public class SelectorProviderUDT extends SelectorProvider {

	/**
	 * system-wide provider instance, for {@link TypeUDT#DATAGRAM} UDT sockets
	 */
	public static final SelectorProviderUDT DATAGRAM = //
	new SelectorProviderUDT(TypeUDT.DATAGRAM);

	/**
	 * system-wide provider instance, for {@link TypeUDT#STREAM} UDT sockets
	 */
	public static final SelectorProviderUDT STREAM = //
	new SelectorProviderUDT(TypeUDT.STREAM);

	/**
	 * {@link TypeUDT} of UDT sockets generated by this provider
	 */
	public final TypeUDT type;

	protected SelectorProviderUDT(final TypeUDT type) {
		this.type = type;
	}

	protected volatile int acceptQueueSize = SocketUDT.DEFAULT_ACCEPT_QUEUE_SIZE;

	public int getAcceptQueueSize() {
		return acceptQueueSize;
	}

	public void setAcceptQueueSize(final int queueSize) {
		acceptQueueSize = queueSize;
	}

	protected volatile int maxSelectorSize = SocketUDT.DEFAULT_MAX_SELECTOR_SIZE;

	public int getMaxSelectorSize() {
		return maxSelectorSize;
	}

	public void setMaxSelectorSize(final int selectorSize) {
		maxSelectorSize = selectorSize;
	}

	/**
	 * FIXME support datagram
	 */
	@Override
	public DatagramChannel openDatagramChannel() throws IOException {
		throw new RuntimeException("TODO");
	}

	@Override
	public Pipe openPipe() throws IOException {
		throw new RuntimeException("feature not available");
	}

	@Override
	public SelectorUDT openSelector() throws IOException {
		return new SelectorUDT(this, maxSelectorSize);
	}

	@Override
	public ChannelServerSocketUDT openServerSocketChannel() throws IOException {
		final SocketUDT serverSocketUDT = new SocketUDT(type);
		return new ChannelServerSocketUDT(this, serverSocketUDT);
	}

	@Override
	public ChannelSocketUDT openSocketChannel() throws IOException {
		final SocketUDT socketUDT = new SocketUDT(type);
		return new ChannelSocketUDT(this, socketUDT);
	}

}
