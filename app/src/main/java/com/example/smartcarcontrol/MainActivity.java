package com.example.smartcarcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    //定义
    private BluetoothAdapter mBluetoothAdapter;
    private TextView text,text2,text3;
    private Button botton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text=(TextView) this.findViewById(R.id.textView);  //已配对
        text2= (TextView) this.findViewById(R.id.textView2); //状态信息
        text3= (TextView) this.findViewById(R.id.textView3); //未配对
        botton=(Button) this.findViewById(R.id.button);

        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        IntentFilter filter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver,filter);
        IntentFilter filter2=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver,filter2);

        botton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                if(!mBluetoothAdapter.isEnabled())
                {
                    mBluetoothAdapter.enable();

                }

                mBluetoothAdapter.startDiscovery();
                text2.setText("正在搜索...");

            }


        });


    }


    public void onDestroy() {

        super.onDestroy();
        //解除注册
        unregisterReceiver(mReceiver);
        Log.e("destory","解除注册");
    }



    //定义广播接收
    private BroadcastReceiver mReceiver=new BroadcastReceiver(){



        @Override
        public void onReceive(Context context, Intent intent) {

            String action=intent.getAction();

            Log.e("ywq", action);
            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(device.getBondState()==BluetoothDevice.BOND_BONDED)
                {    //显示已配对设备
                    text.append("\n"+device.getName()+"==>"+device.getAddress()+"\n");
                }else if(device.getBondState()!=BluetoothDevice.BOND_BONDED)
                {
                    text3.append("\n"+device.getName()+"==>"+device.getAddress()+"\n");
                }

            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){

                text2.setText("搜索完成...");


            }

        }


    };

//        // 注册蓝牙设备被找到时广播接收器
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        registerReceiver(mReceiver, filter);
//        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        registerReceiver(mReceiver, filter);
    }
//
//    // 申请打开蓝牙请求的回调
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "没有蓝牙权限", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }
//
//    /**
//     * 打开蓝牙设备
//     *
//     * @param view
//     */
//    public void enableBluetooth(View view) {
//        // 判断是否支持蓝牙设备
//        if (bluetoothAdapter != null) {
//            // 蓝牙设备是否有效
//            if (!bluetoothAdapter.isEnabled()) {
//                // 打开蓝牙
//                bluetoothAdapter.enable();
//            }
//            // 获取已经配对过的蓝牙设备
//            devices.addAll(bluetoothAdapter.getBondedDevices());
//        }
//    }
//
//    /**
//     * 搜索蓝牙设备
//     *
//     * @param view
//     */
//    public void searchBluetooth(View view) {
//        // 判断是否支持蓝牙设备
//        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
//            setTitle("正在搜索蓝牙设备");
//
//            if (bluetoothAdapter.isDiscovering()) {
//                // 关闭蓝牙搜索
//                bluetoothAdapter.cancelDiscovery();
//            } else {
//                // 发现当前范围的蓝牙设备，异步方法
//                bluetoothAdapter.startDiscovery();
//            }
//        }
//    }
//
//    private BluetoothDevice device;
//
//    /**
//     * 广播接收器接收所有索索过程中的消息
//     */
//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            // 当某一个蓝牙设备被找到，则会收到此消息
//            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
//                // 获取搜索到的蓝牙设备
//                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                // 搜索的设备在之前没有配对过，是一个新的设备
//                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                    devices.add(device);
//                }
//                myAdapter.notifyDataSetChanged();
//            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
//                setTitle("蓝牙设备");
//            }
//
//        }
//    };

}