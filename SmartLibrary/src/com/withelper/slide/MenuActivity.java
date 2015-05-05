package com.withelper.slide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.withelper.R;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemBorrow;
    private ResideMenuItem itemLearning;
    private ResideMenuItem itemNotice;
    private ResideMenuItem itemSuggestion;
    private ResideMenuItem itemSet;
    private ResideMenuItem itemIndex;
    long exitTime = 0; 
    TextView title;
    private Button test;
    private View classview;
    //Intent intent = this.getIntent();
	//Bundle data = intent.getExtras();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        changeFragment(new IndexFragment());
        title = (TextView) findViewById(R.id.titlebar_tv);
		title.setText(R.string.menu1);
		
       // initView();
/*        LayoutInflater factorys = LayoutInflater.from(MenuActivity.this);
        final View index = factorys.inflate(R.layout.index, null);
        ImageView t1 = (ImageView) index.findViewById(R.id.index_brief);
 	   	ImageView t2 = (ImageView) index.findViewById(R.id.index_act);
 	   	ImageView t3 = (ImageView) index.findViewById(R.id.index_navi);
 	   	ImageView t4 = (ImageView) index.findViewById(R.id.index_book);
 	   	t1.setOnClickListener(this);
 	   	t2.setOnClickListener(this);
 	   	t3.setOnClickListener(this);
 	   	t4.setOnClickListener(this);*/
		
		
    }
/*    public void initView(){
    	LayoutInflater factorys = LayoutInflater.from(MenuActivity.this);
        final View index = factorys.inflate(R.layout.index, null);
    	test = (Button) findViewById(R.id.testa);
    	test.setOnClickListener(this);
    }*/
    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        // resideMenu.setBackground(R.drawable.menu_background);
        //resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        //resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);
        // create menu items;
        itemIndex = new ResideMenuItem(this, R.drawable.l_about, "馆区服务");
        itemBorrow = new ResideMenuItem(this, R.drawable.l_logout,  "我的借阅");
        itemLearning = new ResideMenuItem(this, R.drawable.i_sig,  "学习历程");
        itemNotice = new ResideMenuItem(this, R.drawable.l_con, "消息中心");
        itemSuggestion = new ResideMenuItem(this, R.drawable.l_notice, "意见反馈");
        itemSet = new ResideMenuItem(this, R.drawable.l_set, "软件设置");
        
        itemIndex.setOnClickListener(this);
        itemBorrow.setOnClickListener(this);
        itemLearning.setOnClickListener(this);
        itemNotice.setOnClickListener(this);
        itemSuggestion.setOnClickListener(this);
        itemSet.setOnClickListener(this);
        
        resideMenu.addMenuItem(itemIndex, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemBorrow, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLearning, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemNotice, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSuggestion, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSet, ResideMenu.DIRECTION_LEFT);
        
        // You can disable a direction by setting ->
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
/*        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });*/
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
/*    	switch(view.getId())
		{
		case R.id.testa:
			Toast.makeText(getApplicationContext(), "2444",
				     Toast.LENGTH_SHORT).show();
			break;
		case R.id.index_act:
			Toast.makeText(getApplicationContext(), "2444",
				     Toast.LENGTH_SHORT).show();
			break;
		case R.id.index_navi:
			Toast.makeText(getApplicationContext(), "2555",
				     Toast.LENGTH_SHORT).show();
			break;
		case R.id.index_book:
			Toast.makeText(getApplicationContext(), "2666",
				     Toast.LENGTH_SHORT).show();
			break;
			default:
				break;
		}*/
        if (view == itemBorrow){
            changeFragment(new BorrowFragment());
            title = (TextView) findViewById(R.id.titlebar_tv);
    		title.setText(R.string.menu2);
        }else if (view == itemLearning){
            changeFragment(new LearningFragment());
            title = (TextView) findViewById(R.id.titlebar_tv);
    		title.setText(R.string.menu3);
        }else if (view == itemNotice){
            changeFragment(new NoticeFragment());
            title = (TextView) findViewById(R.id.titlebar_tv);
    		title.setText(R.string.menu4);
        }else if (view == itemSuggestion){
            changeFragment(new SuggestionFragment());
            title = (TextView) findViewById(R.id.titlebar_tv);
    		title.setText(R.string.menu5);
        }else if (view == itemSet){
            changeFragment(new SettingFragment());
            title = (TextView) findViewById(R.id.titlebar_tv);
    		title.setText(R.string.menu6);	
        //	finish();
        }else if (view == itemIndex){
            changeFragment(new IndexFragment());
            title = (TextView) findViewById(R.id.titlebar_tv);
    		title.setText(R.string.menu1);	
    		
        }			
    		resideMenu.closeMenu();
        }
    @Override  
        public boolean onKeyDown(int keyCode, KeyEvent event)   
        {  
                  
    	if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)  
                     {  
                               
                             if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000  
                             {  
                              Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();                                  
                              exitTime = System.currentTimeMillis();  
                             }  
                             else  
                             {  
                                 finish();  
                                System.exit(0);  
                             }  
                                       
                             return true;  
                     }  
                     return super.onKeyDown(keyCode, event);  
        }  

/*    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };*/

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？s
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
