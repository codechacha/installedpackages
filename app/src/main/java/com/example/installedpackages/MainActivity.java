package com.example.installedpackages;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<PackageItem> itemList = getInstalledPackages();

        ListViewAdapter adapter = new ListViewAdapter();
        ListView listView = (ListView)findViewById(R.id.listview);
        adapter.setList(itemList);
        listView.setAdapter(adapter);
    }


    private ArrayList<PackageItem> getInstalledPackages() {
        ArrayList<PackageItem> itemList = new ArrayList<PackageItem>();

        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, 0);

        for(ResolveInfo resolveInfo : resolveInfoList){
            PackageItem item = new PackageItem();
            item.icon = resolveInfo.loadIcon(getPackageManager());
            item.label = (String) resolveInfo.loadLabel(getPackageManager());
            item.packageName = resolveInfo.activityInfo.packageName;

            itemList.add(item);
        }

        return itemList;
    }

    public class ListViewAdapter extends BaseAdapter {
        private ArrayList<PackageItem> itemList = new ArrayList<PackageItem>() ;

        public ListViewAdapter() {
        }

        public void setList(ArrayList<PackageItem> list) {
            itemList = list;
        }

        @Override
        public int getCount() {
            return itemList.size() ;
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_item, parent, false);
            }

            ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon) ;
            TextView tvLabel = (TextView) convertView.findViewById(R.id.tv_label) ;
            TextView tvPackageName = (TextView) convertView.findViewById(R.id.tv_packagename) ;

            PackageItem item = itemList.get(position);
            ivIcon.setImageDrawable(item.icon);
            tvLabel.setText(item.label);
            tvPackageName.setText(item.packageName);

            return convertView;
        }


    }

    public class PackageItem {
        public Drawable icon;
        public String label;
        public String packageName;
    }



}
