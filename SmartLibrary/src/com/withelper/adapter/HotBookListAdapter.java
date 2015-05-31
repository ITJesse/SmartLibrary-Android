package com.withelper.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.withelper.R;

public class HotBookListAdapter extends ArrayAdapter<HotBookInfo>{
	
	public HotBookListAdapter(Context context, int textViewResourceId, ArrayList<HotBookInfo> objects)
	{
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder =null;
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(R.layout.hotbooklist_item, null);
			
			holder = new ViewHolder();
			holder.name = (TextView)view.findViewById(R.id.name);
			//holder.author = (TextView)view.findViewById(R.id.author);
			//holder.press = (TextView)view.findViewById(R.id.press);
			//holder.callno = (TextView)view.findViewById(R.id.callno);
			//holder.collections = (TextView)view.findViewById(R.id.collections);
			holder.lend_count = (TextView)view.findViewById(R.id.lend_count);
			holder.lend_ratio = (TextView)view.findViewById(R.id.lend_ratio);
			
			view.setTag(holder);
			
		} else {
			
			view = convertView;
			holder = (ViewHolder)convertView.getTag();
		}
		
		//对控件赋值
        final HotBookInfo singleoder = getItem(position);
        
        if (singleoder != null) {
			holder.name.setText(singleoder.getName());
			//holder.author.setText(singleoder.getAuthor());
			//holder.press.setText(singleoder.getPress());
			//holder.callno.setText(singleoder.getCallno());
			//holder.collections.setText(singleoder.getCollections());
			holder.lend_count.setText(singleoder.getLend_count());
			holder.lend_ratio.setText(singleoder.getLend_ratio());
			
        }
        
		return view;
	}
	
	public class ViewHolder{
		public TextView name;
		//public TextView author;
		//public TextView press;
		//public TextView callno;
		//public TextView collections;
		public TextView lend_count;
		public TextView lend_ratio;
	}
}
