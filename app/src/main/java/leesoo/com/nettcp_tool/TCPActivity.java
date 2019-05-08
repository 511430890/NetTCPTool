package leesoo.com.nettcp_tool;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TCPActivity extends AppCompatActivity {

    @BindView(R.id.rxtv)
    TextView rxtv;
    @BindView(R.id.iptextView)
    TextView iptextView;
    @BindView(R.id.ipaddeditText)
    EditText ipaddeditText;
    @BindView(R.id.netporttextView)
    TextView netporttextView;
    @BindView(R.id.porteditText2)
    EditText porteditText2;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.conll)
    LinearLayout conll;
    @BindView(R.id.sendetv)
    EditText sendetv;
    Handler handler3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);
        ButterKnife.bind(this);
        HandlerThread handlerThread = new HandlerThread("btnthread");
        handlerThread.start();
        handler3 = new Handler(handlerThread.getLooper());

    }

    @OnClick(R.id.button4)
    public void onViewClicked() {
        Log.i("button4", "button4 点击");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String send =null;
                    //Socket socket = new Socket("192.168.1.77", 8888);
                    Socket socket = new Socket();
                    SocketAddress socketAddress = new InetSocketAddress(ipaddeditText.getText().toString(), Integer.valueOf(porteditText2.getText().toString()));
                    socket.connect(socketAddress,1000);
                    if(sendetv.getText()!=null)
                    {
                        send = String.valueOf(sendetv.getText());}
                    socket.setSoTimeout(3000);
                    if (socket.isConnected() && (!socket.isClosed())) {
                        Log.i("Data", "连接成功，准备发送信息");
                        handler3.post(new Runnable() {
                            @Override
                            public void run() {
                                button4.setText("已连接");
                            }
                        });
                        // isSuccess = true;
                    }
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(send.getBytes("UTF-8"));
                    //socket.shutdownOutput();
                    Log.i("Data", "发送信息成功");
                   // socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    public void connectip(View view) {
        Log.i("button4", "xml点击");
    }
}
