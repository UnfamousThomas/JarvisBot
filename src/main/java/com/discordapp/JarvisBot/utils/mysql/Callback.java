package com.discordapp.JarvisBot.utils.mysql;

public interface Callback<D> {

	void call(D data);

	default void fail(String msg) {
	}
}
