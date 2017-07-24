package org.ykc.xscope;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateDataSet {

	private static int dataSeconds = 10; //60

	private static int points = (dataSeconds * 1000) /60;
	public static ObservableList<DataNode> getDataSet(){
		ObservableList<DataNode> dataList = FXCollections.observableArrayList();
		for(int j = 0; j < points; j++){
			for(int i = j*60; i < (j*60 + 60); i++){
				if(i <= (j*60 + 29)){
					dataList.add(new DataNode(i*1000, (short)0, (short)0, (short)10000, (short)5000));
				}
				else {
					dataList.add(new DataNode(i*1000, (short)3300, (short)500, (short)20000, (short)150));
				}
			}
		}
		return dataList;
	}
}
