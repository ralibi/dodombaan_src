package com.ralibi.dodombaan.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 16:11:40 - 11.03.2011
 */
public class ConnectionCloseServerMessage extends ServerMessage {
	// ===========================================================
	// Constants
	// ===========================================================

	/* Connection Flags. */
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_CLOSE = Short.MIN_VALUE;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED = FLAG_MESSAGE_SERVER_CONNECTION_CLOSE + 1;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_REJECTED_PROTOCOL_MISSMATCH = FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED + 1;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_PONG = FLAG_MESSAGE_SERVER_CONNECTION_REJECTED_PROTOCOL_MISSMATCH + 1;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public ConnectionCloseServerMessage() {

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_CONNECTION_CLOSE;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		/* Nothing to read. */
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		/* Nothing to write. */
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
