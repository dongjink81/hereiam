package com.dj81.hereiam.main;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 프로젝트 코어 유틸
 */
public class CoreUtils {

    //데이터를 저장하게 되는 리스트
    private static final void addListItem(ArrayAdapter<String> adapter, List list, String str){
        list.add(str);
        adapter.notifyDataSetChanged();
    }

    public static final void refreshList(ArrayAdapter<String> adapter, List list){
        if(list!=null) list.clear();
        if(adapter!=null) adapter.clear();

        adapter.notifyDataSetChanged();

        List<ContactVO> contacts=DBUtils._instance.getAllContacts();
        for(ContactVO vo : contacts){
            adapter.add(vo.toString());
        }
        adapter.notifyDataSetChanged();
    }
}
