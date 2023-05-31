package com.example.alahamar.apimodel;

import java.util.List;

public class FormsListModel {
	private String msg;
	private List<DataItem> data;
	private String success;

	public String getMsg(){
		return msg;
	}

	public List<DataItem> getData(){
		return data;
	}

	public String getSuccess(){
		return success;
	}
}