package com.shiveluch.cybergames;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class workplace extends AppCompatActivity {
    SharedPreferences database;
    public final static String NAME="name";
    public final static  String PASS="pass";
    public final static String ID="id";
    public final static String ROLE="role";
    public final static String FRACTION="class";
    public static final String SET = "settings";
    public static final String DOMAIN="http://a0568345.xsph.ru/gorynych/";


LinearLayout startLL, setuproles, setupclasses, setuppercs, setupusers, sendMessages, bottomLL;
RelativeLayout masterLL, playerLL;
Button playerbut,masterbut, addrole, removerole, exitbutton, addclass, removeclass, updatebutton,
    changemaster,sending, renewchat;

Context context;
boolean login=false;
ListView roleslist, classlist, percslist, charslist, userslist, desccharslist,
        personsmallinfo, personfullinfo, masterchat, playerchat, roleinfo, fractionlist;
TextView users, roles, percs, classes, opt1, opt2, opt3, sendMes, allrec, messagetext;
String getonlyperson="http://a0568345.xsph.ru/gorynych/getonlyperson.php";
String getroles="http://a0568345.xsph.ru/gorynych/getroles.php";
String getclasses="http://a0568345.xsph.ru/gorynych/getclasses.php";
String getstatus="http://a0568345.xsph.ru/gorynych/getstats.php";
String getpercs="http://a0568345.xsph.ru/gorynych/getpercs.php";
String getCharInfo, roleDescription;
ArrayList<String> chatIDs=new ArrayList<>();
ArrayList<String> chatReceivers = new ArrayList<>();
ArrayList<String> RoleColumns = new ArrayList<>();
ArrayList<MasterChat> mChat= new ArrayList<>();
String getDescription, getLink;
String fillFields = "http://a0568345.xsph.ru/gorynych/getcolumns.php";
String fillRoleFields = "http://a0568345.xsph.ru/gorynych/getrolescolumns.php";
String getmaster;
String getChat;
String getroleinfo;
String getReceiverResourse, getres;
EditText classname, percname, masterpass;
String getRoleID="", getClassID="", getPercID="", getCharID="";
String getplayer, getplayerfull, class_id;
ArrayList<PersonSmall> personsmall=new ArrayList<PersonSmall>();
ArrayList<PersonFull> personfull=new ArrayList<PersonFull>();
ArrayList<Chars> chars = new ArrayList<Chars>();
ArrayList<PlayersList> playersLists = new ArrayList<PlayersList>();
String serviceVar="";


int options=0;
String[] cont_percs={"Имя", "Пароль", "Фракция", "Роль", "Описание"};
String[] cont_persons={"Имя", "Описание", "Роль", "Фракция"};
String[] cont_roles={"Роль", "Фракция", "Описание"};
String[] cont_player={"", "", "", ""};
String[] perc_status={"", "", "", "",""};

String [] diff_percs={"","", "", "",""};
String [] meaning={"","","","","","","","","","",""};
ArrayList<String> fields = new ArrayList<>();
ArrayList<Roleclass> allroles = new ArrayList<Roleclass>();
ArrayList<Roleclass> allclasses = new ArrayList<Roleclass>();
boolean roleclass=false;
TextToSpeech tts;
Timer mTimer;
TimerTask mMyTimerTask;
Activity activity;

    boolean newchar=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workplace);
        database=getSharedPreferences(SET, Context.MODE_PRIVATE);
        activity=this;
        initVisualElements();
        startLoad();
        TTS();
        loadInterface();
        loadPref();
        startTimer();
        decrypt();

       // audio();
    }

    private void decrypt() {
        SecretKeySpec key=generateSymmetric();
        String newSymmetricKeyString = ConvertSymmetricKeyToString(key);
        Log.d("symm",newSymmetricKeyString);
    }

    public static String ConvertSymmetricKeyToString(SecretKeySpec key) {

        String symmetric_key = null;

        symmetric_key = Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
        return symmetric_key;
    }

    public static SecretKeySpec generateSymmetric() {

        // Set up secret key spec for 128-bit AES encryption and decryption
        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");

            System.out.println("AES KEY: " + sks);
        } catch (Exception e) {
            Log.e("AES", "AES secret key spec error");

        }
        return sks;
    }

    private void startTimer() {
//        mTimer = new Timer();
//        mMyTimerTask = new MyTimerTask();
//        mTimer.schedule(mMyTimerTask, 10000, 10000);
    }

    private void loadPref() {
        System.out.println(database.getString(ROLE,""));
    }

    private void loadInterface() {
        String currentrole=database.getString(ROLE,"");
        if (currentrole.equals(""))
        {
        }
        if (currentrole.equals("master"))
        {
            startLL.setVisibility(GONE);
            masterLL.setVisibility(View.VISIBLE);
            playerLL.setVisibility(GONE);
            bottomLL.setVisibility(View.VISIBLE);
        }
         if (currentrole.equals("player"))
        {
            startLL.setVisibility(GONE);
            masterLL.setVisibility(GONE);
            playerLL.setVisibility(View.VISIBLE);
            bottomLL.setVisibility(View.VISIBLE);
            getplayerfull="http://a0568345.xsph.ru/gorynych/getplayerfull.php/get.php?nom="
                    +database.getString(NAME,"")+"&dp="+database.getString(PASS,"");
            getplayer="http://a0568345.xsph.ru/gorynych/getplayerinfo.php/get.php?nom="
                    +database.getString(NAME,"")+"&dp="+database.getString(PASS,"");
            System.out.println(getplayer);
            getJSON(getplayer);
            getJSON(getplayerfull);
        }


    }

    private void TTS() {
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status==TextToSpeech.SUCCESS)
                {
                    int lang=tts.setLanguage(Locale.US);
                    if (lang==TextToSpeech.LANG_MISSING_DATA || lang==TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        sendToast("Язык не поддерживается");
                    }


                }
            }
        });
        String data="Milord";
        tts.setSpeechRate(1f);
        tts.speak(data,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    public void startLoad()
    {
        getJSON(getclasses);
        getJSON(getpercs);
        getJSON(getroles);
        getJSON(fillFields);
        getJSON(getonlyperson);
        getJSON(fillRoleFields);
    }

    public void initVisualElements()

    {
        startLL=findViewById(R.id.startLL);
        playerLL=findViewById(R.id.playerLL);
        playerbut=findViewById(R.id.playerbut);
        masterbut=findViewById(R.id.masterbut);
        context=getApplicationContext();
        masterLL=findViewById(R.id.masterLL);
        roleslist=findViewById(R.id.roleslist);
        roles=findViewById(R.id.roles);
        classes=findViewById(R.id.classes);
        setuproles=findViewById(R.id.setuproles);
        addrole=findViewById(R.id.addrole);
        removerole=findViewById(R.id.removerole);
        exitbutton=findViewById(R.id.exit);
        addrole = findViewById(R.id.addrole);
        removerole = findViewById(R.id.removerole);
        setupclasses=findViewById(R.id.setupclasses);
        setuppercs=findViewById(R.id.setuppercs);
        setupusers=findViewById(R.id.setupusers);
        addclass=findViewById(R.id.addclass);
        removeclass=findViewById(R.id.removeclass);
        classname=findViewById(R.id.classname);
        classlist=findViewById(R.id.classlist);
        users=findViewById(R.id.users);
        percs=findViewById(R.id.percs);
        opt1=findViewById(R.id.opt1);
        opt2=findViewById(R.id.opt2);
        opt3=findViewById(R.id.opt3);
        sendMes=findViewById(R.id.messages);
        percslist=findViewById(R.id.percslist);
        percname=findViewById(R.id.percsname);
        updatebutton=findViewById(R.id.updatepercs);
        charslist=findViewById(R.id.charslist);
        userslist=findViewById(R.id.userslist);
        desccharslist=findViewById(R.id.desccharslist);
        bottomLL=findViewById(R.id.bottomLL);
        personsmallinfo=findViewById(R.id.personsmallinfo);
        personfullinfo=findViewById(R.id.personfullinfo);
        sendMessages=findViewById(R.id.sendMessages);
        changemaster = findViewById(R.id.changemaster);
        sending=findViewById(R.id.sending);
        allrec=findViewById(R.id.allrec);
        messagetext=findViewById(R.id.messagetext);
        masterpass=findViewById(R.id.masterpass);
        masterchat=findViewById(R.id.masterchat);
        playerchat=findViewById(R.id.playerchat);
        renewchat=findViewById(R.id.renewchat);
        roleinfo=findViewById(R.id.roledesc);
        fractionlist = findViewById(R.id.fractionlist);

        roleinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView isField=view.findViewById(R.id.a_field);
                TextView isID=view.findViewById(R.id.a_id);
                TextView isData = view.findViewById(R.id.a_fielddat);
                String getData = isData.getText().toString();
                String getID = isID.getText().toString();
                String getField = isField.getText().toString();
                Log.d ("Get position","is position: "+position);
                if (position==2){
                 showDescriptionDialog(getDescription, getField,true, getID);
                }

                if (position == 1)
                {
                    getJSON(getclasses);
               showRKdialog(allclasses, getData, getID, getField);
                }

                if (position != 1 && position!=2)
                {

                    showChangePercDialog(getData,getID,getField, position, true);

                }

            }
        });
        renewchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChat="http://a0568345.xsph.ru/gorynych/getchat.php/get.php?nom="+"\""+
                        database.getString(ID,"")+"\"";
                getJSON(getChat);
            }
        });

        changemaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (masterpass.getText().toString().length()<3)
                {
                    sendToast ("Cлишком короткий пароль");
                    return;
                }
                new uploadAsyncTask().execute("updatemaster",masterpass.getText().toString(),"","","","" );
                sendToast ("Установлен пароль " + masterpass.getText().toString()+", "+"требуется повторный вход");
                SharedPreferences.Editor editor = database.edit();
                editor.clear();
                editor.apply();
                masterLL.setVisibility(GONE);
                bottomLL.setVisibility(GONE);
                startLL.setVisibility(View.VISIBLE);

            }
        });
        final String[] allIDs = {""};
        final String[] allPersons = {""};
        final String[] faction = {""};

        fractionlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView isID=view.findViewById(R.id.a_id);
                TextView isFaction=view.findViewById(R.id.a_class);
                String getID = isID.getText().toString();
                String getFaction = isFaction.getText().toString();


                allrec.setText("Получатель: "+getFaction);
                faction[0] ="f"+getID+"#"+getFaction;

            }
        });

userslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView isID=view.findViewById(R.id.a_id);
        TextView isPerson = view.findViewById(R.id.a_person);
        String getPerson = isPerson.getText().toString();
        String getID=isID.getText().toString();
        String _id="\""+getID+"\"";
        String _persons= "\""+getPerson+"\"";

        String parsing1="";
        faction[0] = "";
       // chatIDs.add("\""+getID+"\"");
        for (int i=0;i<chatIDs.size();i++)
        {
          allIDs[0] +=chatIDs.get(i);
          allPersons[0] +=chatReceivers.get(i);

        }


        if (allIDs[0].contains(_id))
        {
            for (int i=0;i<chatIDs.size();i++)
            {
                if (chatIDs.get(i).contains(_id))
                {
                    chatIDs.remove(i);
                    chatReceivers.remove(i);
                }
            }
        }

        if (!allIDs[0].contains(_id))
        {
            chatIDs.add(_id);
            chatReceivers.add(_persons);
        }
        allIDs[0] ="";
        allPersons[0] ="";
        for (int i=0;i<chatIDs.size();i++)
        {
            allIDs[0] +=chatIDs.get(i);
            allPersons[0] +=chatReceivers.get(i);

            parsing1 = allPersons[0].replace("\"\"", ", ");
            parsing1=parsing1.replace("\"","");

        }
        sOut(parsing1);
        if (parsing1.length()==0) {allrec.setText ("Получатели: Все");}
        else {
            allrec.setText("Получатели: "+parsing1);
        }
        for (int i=0;i<chatIDs.size();i++)
        {
        }
    }
});

sending.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);
        if (!faction[0].contains("#"))
        {
        String parsing1 = null;
        if (allIDs[0].length()==0) allIDs[0]="\"0\"";
        if (allPersons[0].length()==0) allPersons[0]="All";
        else
        {
            parsing1 = allPersons[0].replace("\"\"", ", ");
            parsing1=parsing1.replace("\"","");
        }

        new uploadAsyncTask().execute("addmessage", messagetext.getText().toString(), allIDs[0],
                parsing1,timeText);
        allIDs[0]="";
        allPersons[0]="";}

        if (faction[0].contains("#"))
        {
            String [] split = faction[0].split("#");
            new uploadAsyncTask().execute("addmessage", messagetext.getText().toString(), split[0],
                    split[1],timeText);
        }
        messagetext.setText("");
        allrec.setText("Получатели: Все");
        getChat="http://a0568345.xsph.ru/gorynych/getchat.php/get.php?nom="+"\""+"&dp=f";
        getJSON(getChat);
    }
});

masterchat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView isID=view.findViewById(R.id.a_id);
        String getID=isID.getText().toString();
        new uploadAsyncTask().execute("deletemessage",getID,"","","","");

        return false;
    }
});

        personsmallinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDescription=("http://a0568345.xsph.ru/gorynych/getdescription.php/get.php?nom="
                        +database.getString(ID,""));
                getJSON(getDescription);
            }
        });


        sendMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setuproles.setVisibility(GONE);
                setupclasses.setVisibility(GONE);
                setupusers.setVisibility(GONE);
                setuppercs.setVisibility(GONE);
                sendMessages.setVisibility(View.VISIBLE);
                users.setTextColor(getResources().getColor(R.color.cyberblue));
                roles.setTextColor(getResources().getColor(R.color.cyberblue));
                percs.setTextColor(getResources().getColor(R.color.cyberblue));
                classes.setTextColor(getResources().getColor(R.color.cyberblue));
                sendMes.setTextColor(getResources().getColor(R.color.cybergreen));
                chatIDs.clear();
                allIDs[0]="";
                allPersons[0]="";
                messagetext.setText("");
                getJSON(getonlyperson);
                getJSON(getclasses);
                getChat="http://a0568345.xsph.ru/gorynych/getchat.php/get.php?nom="+"\""+"&dp=f";
                getJSON(getChat);
//                JSONObject a_percs=new JSONObject();
//                for (int i=0;i<cont_percs.length;i++)
//                {
//                    try {
//                        a_percs.put("perc"+i,cont_percs[i]);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                String jsons=""+a_percs;
//                if (jsons.contains("\"Пароль\""))
//                {
//                }


            }
        });

        personfullinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getplayerfull="http://a0568345.xsph.ru/gorynych/getplayerfull.php/get.php?nom="
                        +database.getString(NAME,"")+"&dp="+database.getString(PASS,"");
                getJSON(getplayerfull);
                TextView isID=view.findViewById(R.id.a_id);
                TextView isPercName=view.findViewById(R.id.a_percname);
                TextView isPercData=view.findViewById(R.id.a_percdata);
              //  Button bTrans=view.findViewById(R.id.b_transfer);
                int getID = Integer.parseInt(isID.getText().toString());
                String getPercName=isPercName.getText().toString();
                String getPercData=isPercData.getText().toString();
                String getField="perc"+(position+1);
                int getPlayerID=Integer.parseInt(database.getString(ID,""));

               if (getID!=1)
                {
                    sendToast("Этот параметр может быть изменен только мастером или изменяется автоматически");
                }
                else
                {
                  //  getJSON(getonlyperson);
                    showTransferDialog(getPercData, getPlayerID,getField);
                }
            }
        });

        charslist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView isID=view.findViewById(R.id.a_id);
                String getID=isID.getText().toString();
                showAddremoveDialog(getID);
                return false;
            }
        });

        users.setOnClickListener(v -> {
            setuproles.setVisibility(GONE);
            setupclasses.setVisibility(GONE);
            setupusers.setVisibility(View.VISIBLE);
            sendMessages.setVisibility(GONE);
            setuppercs.setVisibility(GONE);
            users.setTextColor(getResources().getColor(R.color.cybergreen));
            roles.setTextColor(getResources().getColor(R.color.cyberblue));
            percs.setTextColor(getResources().getColor(R.color.cyberblue));
            classes.setTextColor(getResources().getColor(R.color.cyberblue));
            sendMes.setTextColor(getResources().getColor(R.color.cyberblue));

            getJSON(getonlyperson);
        });



        opt1.setOnClickListener(v -> {
            options=1;
            opt1.setTextColor(getResources().getColor(R.color.cybergreen));
            opt2.setTextColor(getResources().getColor(R.color.cyberblue));
            opt3.setTextColor(getResources().getColor(R.color.cyberblue));
        });
        opt2.setOnClickListener(v -> {
options=2;
            opt1.setTextColor(getResources().getColor(R.color.cyberblue));
            opt2.setTextColor(getResources().getColor(R.color.cybergreen));
            opt3.setTextColor(getResources().getColor(R.color.cyberblue));
        });

        opt3.setOnClickListener(v -> {
options=3;
            opt1.setTextColor(getResources().getColor(R.color.cyberblue));
            opt2.setTextColor(getResources().getColor(R.color.cyberblue));
            opt3.setTextColor(getResources().getColor(R.color.cybergreen));
        });

        updatebutton.setOnClickListener(v -> {
            String isPerc=percname.getText().toString();
        if (percname.getText().toString().length()<1){isPerc="Пустое поле";}

        new uploadAsyncTask().execute("updateperc",isPerc,""+options,getPercID,"");
        });


percslist.setOnItemClickListener((parent, view, position, id) -> {
    TextView isPerc=view.findViewById(R.id.a_perc);
    TextView sid=view.findViewById(R.id.p_lon);
    TextView pID=view.findViewById(R.id.a_id);
    String sPerc=isPerc.getText().toString();
    String sSid=sid.getText().toString();
    System.out.println(sSid);
    updatebutton.setVisibility(View.VISIBLE);
    getPercID=pID.getText().toString();
    percname.setText(sPerc);
    if (sSid.equals("1"))
    {opt1.setTextColor(getResources().getColor(R.color.cybergreen));
        opt2.setTextColor(getResources().getColor(R.color.cyberblue));
        opt3.setTextColor(getResources().getColor(R.color.cyberblue));
System.out.println("One");
    }

    if (sSid.equals("2"))
    {opt1.setTextColor(getResources().getColor(R.color.cyberblue));
        opt2.setTextColor(getResources().getColor(R.color.cybergreen));
        opt3.setTextColor(getResources().getColor(R.color.cyberblue));

    }
    if (sSid.equals("3"))
    {opt1.setTextColor(getResources().getColor(R.color.cyberblue));
        opt2.setTextColor(getResources().getColor(R.color.cyberblue));
        opt3.setTextColor(getResources().getColor(R.color.cybergreen));

    }

});

charslist.setOnItemClickListener((parent, view, position, id) -> {
    TextView isID=view.findViewById(R.id.a_id);
    String getID=isID.getText().toString();
   // System.out.println(getID);
    getCharInfo = "http://a0568345.xsph.ru/gorynych/getcharinfo.php/get.php?nom="+getID;
    getJSON(getCharInfo);
});

        percs.setOnClickListener(v -> {
            updatebutton.setVisibility(GONE);
            percname.setText("");
            options=0;
            getPercID="";
            setuproles.setVisibility(GONE);
            setupclasses.setVisibility(GONE);
            setupusers.setVisibility(GONE);
            sendMessages.setVisibility(GONE);
            setuppercs.setVisibility(View.VISIBLE);
            users.setTextColor(getResources().getColor(R.color.cyberblue));
            roles.setTextColor(getResources().getColor(R.color.cyberblue));
            percs.setTextColor(getResources().getColor(R.color.cybergreen));
            classes.setTextColor(getResources().getColor(R.color.cyberblue));
            sendMes.setTextColor(getResources().getColor(R.color.cyberblue));

            getJSON(getstatus);
            getJSON(getpercs);


        });

desccharslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView isInfo=view.findViewById(R.id.a_descript);
        String getInfo=isInfo.getText().toString();
        String field=fields.get(position+1);
        if (position==2) return;
        if (position!=3 &&position!=4)
        showChangePercDialog(getInfo,getCharID,field, position, false);


            if (position==3) {
                roleclass=true;
                getJSON(getroles);
                showSelectRolesDialog(allroles,getInfo,getCharID,field);

            }


    }
});

        classlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView a_id=view.findViewById(R.id.a_id);
                TextView a_class = view.findViewById(R.id.a_class);
                classname.setText(a_class.getText().toString());
                getClassID = a_id.getText().toString();

            }
        });

        removeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new uploadAsyncTask().execute("delclass",getClassID,"","","");
            }
        });

        addclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _classname=classname.getText().toString();
                if(classname.length()>1)
                    new uploadAsyncTask().execute("addclass",_classname,"","","");

                else {sendToast("Недостаточная длина названия");return;}
            }
        });

        removerole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new uploadAsyncTask().execute("delrole",getRoleID,"","","");
                roleinfo.setAdapter(null);
            }
        });

       // playerLL=findViewById(R.id.playerLL)

        roleslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView a_id=view.findViewById(R.id.a_id);
                TextView a_role = view.findViewById(R.id.a_role);
                getRoleID = a_id.getText().toString();
                getroleinfo="http://a0568345.xsph.ru/gorynych/getroleinfo.php/get.php?nom="+getRoleID;
                getJSON (getroleinfo);
                System.out.println(getRoleID);
            }
        });
addrole.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            new uploadAsyncTask().execute("addrole","Новая роль","","","");
            getJSON(getroles);

    }
});
        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });

        roles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setuproles.setVisibility(View.VISIBLE);
                setupclasses.setVisibility(GONE);
                setupusers.setVisibility(GONE);
                setuppercs.setVisibility(GONE);
                sendMessages.setVisibility(GONE);
                users.setTextColor(getResources().getColor(R.color.cyberblue));
                roles.setTextColor(getResources().getColor(R.color.cybergreen));
                percs.setTextColor(getResources().getColor(R.color.cyberblue));
                classes.setTextColor(getResources().getColor(R.color.cyberblue));
                sendMes.setTextColor(getResources().getColor(R.color.cyberblue));
                getJSON(getroles);

            }
        });

        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setuproles.setVisibility(GONE);
                setupclasses.setVisibility(View.VISIBLE);
                setupusers.setVisibility(GONE);
                setuppercs.setVisibility(GONE);
                sendMessages.setVisibility(GONE);
                users.setTextColor(getResources().getColor(R.color.cyberblue));
                roles.setTextColor(getResources().getColor(R.color.cyberblue));
                percs.setTextColor(getResources().getColor(R.color.cyberblue));
                classes.setTextColor(getResources().getColor(R.color.cybergreen));
                sendMes.setTextColor(getResources().getColor(R.color.cyberblue));

                getJSON(getclasses);
            }
        });

        playerbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeout = AnimationUtils.loadAnimation(context, R.anim.fadeout);
                fadeout.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Audio audio = new Audio(context);
                        audio.playClick();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
startLL.setVisibility(GONE);
login=false;
showDialog();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                startLL.startAnimation(fadeout);



            }
        });
        masterbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeout = AnimationUtils.loadAnimation(context, R.anim.fadeout);
                fadeout.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Audio audio = new Audio(context);
                        audio.playClick();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startLL.setVisibility(GONE);
                        login=true;
                        showDialog();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                startLL.startAnimation(fadeout);



            }
        });


    }
    AlertDialog.Builder alert;
    AlertDialog dialog;
    EditText acc_name, acc_pass;

    private void showDialog()
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogue,null);
        LinearLayout approve, dismiss;
        TextView mess;
        dismiss=view.findViewById(R.id.dismiss);
        approve=view.findViewById(R.id.approve);
        acc_name=view.findViewById(R.id.acc_name);
        acc_pass=view.findViewById(R.id.acc_pass);
        alert.setView(view);
        alert.setCancelable(false);
       // mess.setText("Удалить сообщение?");
        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss(); startLL.setVisibility(View.VISIBLE);}
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new uploadAsyncTask().execute("delpost",message,"","","");
                if (login) {
                    getmaster="http://a0568345.xsph.ru/gorynych/getmaster.php/get.php?nom="
                            +acc_name.getText().toString()+"&dp="+acc_pass.getText().toString();
                    getJSON(getmaster);
                }

                if (!login) {
                    getplayer="http://a0568345.xsph.ru/gorynych/getplayerinfo.php/get.php?nom="
                            +acc_name.getText().toString()+"&dp="+acc_pass.getText().toString();
                    getplayerfull="http://a0568345.xsph.ru/gorynych/getplayerinfo.php/get.php?nom="
                            +acc_name.getText().toString()+"&dp="+acc_pass.getText().toString();
                    getJSON(getplayer);
                    getJSON(getplayerfull);



                }

            }
        });

        dialog.show();
    }

    private void showDescriptionDialog(String description,
                                       String getPercID, boolean role, String roleID)
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        EditText desc,link;
        Button loadlink;
        View view = inflater.inflate(R.layout.descdialog,null);
        LinearLayout approve, dismiss;
        TextView mess;
        String isLink;
        final String[] toUpload = new String[1];
        dismiss=view.findViewById(R.id.dismiss);
        approve=view.findViewById(R.id.approve);
        desc=view.findViewById(R.id.maindesc);
        loadlink=view.findViewById(R.id.loadlink);
        link=view.findViewById(R.id.link);
        if (role)
        {
            desc.setEnabled(true);
            link.setEnabled(true);
        }
        else
        {
            desc.setEnabled(false);
            link.setEnabled(false);
            link.setVisibility(GONE);
            loadlink.setVisibility(View.VISIBLE);
        }
        if (description.contains("#")) {
            String[] split = description.split("#");
            desc.setText(split[0]);
            if (role)
                link.setText(split[1]);
            else {
                link.setVisibility(GONE);
                desc.setEnabled(false);
                loadlink.setVisibility(View.VISIBLE);
                loadlink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (split[1].contains("http")) {
                            Intent browserIntent = new
                                    Intent(Intent.ACTION_VIEW, Uri.parse(split[1]));
                            startActivity(browserIntent);
                        } else sendToast("Данные отсутствуют");
                    }
                });
            }
        }
        else
        {
            desc.setText(description);
        }

        alert.setView(view);
        alert.setCancelable(false);
        // mess.setText("Удалить сообщение?");
        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();}
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (link.getText().toString().length()>0) {
                    toUpload[0] = desc.getText().toString()+"#"+link.getText().toString();
                }
                else
                {
                    toUpload[0] = desc.getText().toString();
                }
                new uploadAsyncTask().execute("updaterole","description", toUpload[0],roleID, "");
                getroleinfo="http://a0568345.xsph.ru/gorynych/getroleinfo.php/get.php?nom="+roleID;
                getJSON (getroleinfo);
dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void showAddremoveDialog(String id)
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.addremoveuserdialog,null);
        LinearLayout create, delete, dismiss;
        TextView mess;
        dismiss=view.findViewById(R.id.dismiss);
        create=view.findViewById(R.id.create);
        delete=view.findViewById(R.id.delete);

        alert.setView(view);
        alert.setCancelable(false);
        // mess.setText("Удалить сообщение?");
        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();}
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new uploadAsyncTask().execute("delpost",message,"","","");
new uploadAsyncTask().execute("adduser","","","","");
getJSON(getonlyperson);
dialog.dismiss();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new uploadAsyncTask().execute("deleteuser",id,"","","","");
                getJSON(getonlyperson);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showRKdialog(ArrayList<Roleclass> rk, String text,
                              String id, String _isField)
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.rolesclassesdialog,null);
        LinearLayout approve, dismiss;
        ListView roleclasslist;
        String txt="ТЕКУЩАЯ ФРАКЦИЯ:\n";
        TextView mess=view.findViewById(R.id.currentroleclass);
        mess.setText(txt+text);
        dismiss=view.findViewById(R.id.dismiss);
        approve=view.findViewById(R.id.save);
        roleclasslist=view.findViewById(R.id.roleclasslist);
        acc_name=view.findViewById(R.id.acc_name);
        acc_pass=view.findViewById(R.id.acc_pass);
        String _field = "";
        final String[] getRKid = new String[1];
        alert.setView(view);
        alert.setCancelable(false);
        // mess.setText("Удалить сообщение?");

        String finalTxt = txt;
    roleclasslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView isRK=view.findViewById(R.id.a_role);
            TextView isRKid=view.findViewById(R.id.a_id);
            String getRK=isRK.getText().toString();
            getRKid[0] = isRKid.getText().toString();
            mess.setText("Выбранная роль: " +getRK);
        }
    });

            RoleClassAdapter rolesAdapter=new RoleClassAdapter(this, rk,this);
            roleclasslist.setAdapter(rolesAdapter);


        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();}
        });
//
        String final_field = _field;
        approve.setOnClickListener(v -> {
            if (getRKid[0].length()<1) {
                sendToast("Не выбран ни один элемент");
                return;
            }

            new uploadAsyncTask().execute("updaterole", _isField, getRKid[0], id,"");

            dialog.dismiss();
           // getJSON(getroles);
            getroleinfo="http://a0568345.xsph.ru/gorynych/getroleinfo.php/get.php?nom="+getRoleID;
            getJSON (getroleinfo);


        });

        dialog.show();
    }
    private void showSelectRolesDialog(ArrayList<Roleclass> rk, String text,
                              String id, String _isField)
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.rolesdialog,null);
        LinearLayout approve, dismiss;
        ListView roleclasslist;
        String txt="ТЕКУЩАЯ РОЛЬ:\n";
        TextView mess=view.findViewById(R.id.currentrole);
        mess.setText(txt+text);
        dismiss=view.findViewById(R.id.dismiss);
        approve=view.findViewById(R.id.save);
        roleclasslist=view.findViewById(R.id.d_rolelist);
        String _field = "";
        final String[] getRKid = new String[1];
        alert.setView(view);
        alert.setCancelable(false);
        // mess.setText("Удалить сообщение?");

        String finalTxt = txt;
        roleclasslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView isRK=view.findViewById(R.id.a_role);
                TextView isRKid=view.findViewById(R.id.a_id);
                String getRK=isRK.getText().toString();
                getRKid[0] = isRKid.getText().toString();
                mess.setText("Выбранная роль: " +getRK);
            }
        });

        RoleClassAdapter rolesAdapter=new RoleClassAdapter(this, rk,this);
        roleclasslist.setAdapter(rolesAdapter);


        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();}
        });
//
        String final_field = _field;
        approve.setOnClickListener(v -> {
            if (getRKid[0].length()<1) {
                sendToast("Не выбран ни один элемент");
                return;
            }

            new uploadAsyncTask().execute("updateplayerfromrole", getCharID, getRKid[0],"", "");
            getCharInfo = "http://a0568345.xsph.ru/gorynych/getcharinfo.php/get.php?nom="+getCharID;
            getJSON(getCharInfo);
            dialog.dismiss();
           // getJSON(getroles);



        });

        dialog.show();
    }

AlertDialog transferDialog;
AlertDialog.Builder transferAlert;


    private void showTransferDialog(String percdata, int _getID, String getField)
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            transferAlert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            transferAlert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.transferdialog,null);
        EditText _percdata=view.findViewById(R.id.percdata);
        EditText _receiver=view.findViewById(R.id.receqivername);
        LinearLayout transfer, dismiss;
        ListView playerslist;
        String txt="";
        TextView mess=view.findViewById(R.id.currentperc);
        dismiss=view.findViewById(R.id.dismiss);
        transfer=view.findViewById(R.id.transfer);
        playerslist=view.findViewById(R.id.playerslist);
        String _field = "";
        for (int i=0;i<chars.size();i++)
        {
        }
        CharsAdapter playersListAdapter=new CharsAdapter(context, chars,activity);
        playerslist.setAdapter(playersListAdapter);
        ArrayList<Chars> searching=new ArrayList<Chars>();
        _receiver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0)
                {
                    searching.clear();
                    for (int i=0;i< chars.size();i++)
                    {
                        String currentchar=chars.get(i).charName.toLowerCase(Locale.ROOT);
                        if (currentchar.contains(s))
                        {

                            searching.add(new Chars(chars.get(i).charName, chars.get(i).charRole,
                                    chars.get(i).charClass, chars.get(i).charID, chars.get(i).charTime));
                        }
                    }
                    CharsAdapter playersListAdapter=new CharsAdapter(context, searching,activity);
                    playerslist.setAdapter(playersListAdapter);
                }
                else
                {
                    CharsAdapter playersListAdapter=new CharsAdapter(context, chars,activity);
                    playerslist.setAdapter(playersListAdapter);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _percdata.setHint("Количество\n(не более "+percdata+")");
        transferAlert.setView(view);
        transferAlert.setCancelable(false);
        // mess.setText("Удалить сообщение?");
        final boolean[] isReceiver = {false};
        final String[] ReceiverID = new String[1];
        playerslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView isPlayer=view.findViewById(R.id.a_person);
                TextView isID=view.findViewById(R.id.a_id);
                String getPlayer=isPlayer.getText().toString();
                ReceiverID[0] =isID.getText().toString();
                getres=DOMAIN+"getresource.php/get.php?nom="+getField+"&dp="+ReceiverID[0];
                getJSON(getres);
                isReceiver[0] =true;
                mess.setText("Получатель: "+getPlayer);

//                String getRK=isRK.getText().toString();
//
//                getRKid[0] = isRKid.getText().toString();
//
//                mess.setText(finalTxt +getRK);
            }
        });

//        if (_roleclass)
//        {
//            _field=fields.get(4);

//        }
//
//        if (!_roleclass)
//        {
//            RoleClassAdapter rolesAdapter=new RoleClassAdapter(this, allclasses,this);
//            roleclasslist.setAdapter(rolesAdapter);
//            _field=fields.get(3);
//        }
        transferDialog = transferAlert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {transferDialog.dismiss();}
        });
//
        String final_field = _field;
        transfer.setOnClickListener(v -> {
            int toTransfer;
            if (_percdata.getText().toString().length()==0) {
                toTransfer=0;}
            if (_percdata.getText().toString().length()==0) {
                toTransfer=0;}
            else toTransfer=Integer.parseInt(_percdata.getText().toString());
            int getRes=Integer.parseInt(percdata.replace(" ",""));
            if (!isReceiver[0]) {sendToast ("Не указан получатель");return;}
            if (getRes<toTransfer) {sendToast("Не имеется в наличии");return;}
            if (getReceiverResourse.equals("")) getReceiverResourse="0";
            long toResult=Integer.parseInt(getReceiverResourse)+toTransfer;
            if (toResult>999999999) toResult=999999999;
//уменьшение для владельца
          new uploadAsyncTask().execute("updatechar", getField,""+(getRes-toTransfer), ""+_getID,"");
//Увеличесние для получателя
            new uploadAsyncTask().execute("updatechar", getField,
                    ""+toResult, ReceiverID[0],"");
//            dialog.dismiss();
//            getCharInfo = "http://a0568345.xsph.ru/gorynych/getcharinfo.php/get.php?nom="+id;
//            getJSON(getCharInfo);
//            getJSON(getonlyperson);
            transferDialog.dismiss();

        });

        transferDialog.show();
    }


    private void showChangePercDialog(String text, String id, String field,int position, boolean role)
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.changechardialog,null);
        LinearLayout approve, dismiss;
        TextView mess;
        dismiss=view.findViewById(R.id.dismiss);
        approve=view.findViewById(R.id.save);
        acc_name=view.findViewById(R.id.changedata);
        if (!role)
        {if (position>4) acc_name.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
        acc_name.setText(text);}

        if (role){
        if (position>2)
        {
            acc_name.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
            acc_name.setText(text);
        }}

        alert.setView(view);
        alert.setCancelable(false);
        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();}
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!role)
                {  new uploadAsyncTask().execute("updatechar", field, acc_name.getText().toString(), id, "");
                dialog.dismiss();
                getCharInfo = "http://a0568345.xsph.ru/gorynych/getcharinfo.php/get.php?nom=" + id;
                getJSON(getCharInfo);
                getJSON(getonlyperson);
                }

                if (role)
                {  new uploadAsyncTask().execute("updaterole", field, acc_name.getText().toString(), id, "");
                    dialog.dismiss();
                    getroleinfo="http://a0568345.xsph.ru/gorynych/getroleinfo.php/get.php?nom="+id;
                    getJSON (getroleinfo);
                    getJSON(getroles);
                }

            }
        });

        dialog.show();
    }

    private void showTruncateDialog()
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.truncatedialog,null);
        LinearLayout truncate, dismiss;
        TextView mess;
        EditText truncpass=view.findViewById(R.id.truncpass);
        dismiss=view.findViewById(R.id.dismiss);
       truncate=view.findViewById(R.id.truncate);
        alert.setView(view);
        alert.setCancelable(false);
        // mess.setText("Удалить сообщение?");
        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();}
        });

        truncate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(truncpass.getText().toString() ==getResources().getString(R.string.basic)))
                {
                    sendToast("Пароль не опознан");
                    return;
                }
                new uploadAsyncTask().execute("getnew",truncpass.getText().toString(),"","","" );
                startLoad();


            }
        });

        dialog.show();
    }


    private void showExitDialog()
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            alert=new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.exitdialog,null);
        LinearLayout approve, dismiss;
        TextView mess;
        dismiss=view.findViewById(R.id.dismiss);
        approve=view.findViewById(R.id.approve);
        alert.setView(view);
        alert.setCancelable(false);
        // mess.setText("Удалить сообщение?");
        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();}
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new uploadAsyncTask().execute("delpost",message,"","","");
                SharedPreferences.Editor editor=database.edit();
                editor.clear();
                editor.apply();
                masterLL.setVisibility(GONE);
                playerLL.setVisibility(GONE);
                startLL.setVisibility(View.VISIBLE);
                bottomLL.setVisibility(GONE);
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    public void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s != null) {
                    try {

                        if (urlWebService == getroles)
                            loadRoles(s);

                        if (urlWebService == getmaster)
                            loadMaster(s);

                        if (urlWebService == getplayer)
                            loadPlayer(s);

                        if (urlWebService == getplayerfull)
                            loadPlayerFull(s);

                        if (urlWebService == getclasses)
                            loadClasses(s);

                        if (urlWebService == getstatus)
                            loadStatus(s);

                        if (urlWebService == getpercs)
                            loadPercs(s);

                        if (urlWebService == getonlyperson)
                            loadOnlyPerson(s);

                        if (urlWebService == getres)
                            loadRes(s);

                        if (urlWebService == getCharInfo)
                            loadCharInfo(s);

                        if (urlWebService == fillFields)
                            loadFields(s);

                        if (urlWebService == getDescription)
                            loadDescription(s);

                        if (urlWebService == getLink)
                            loadLink(s);
                        if (urlWebService == getChat)
                            loadChat(s);
                        if (urlWebService == fillRoleFields)
                            loadRoleColumns(s);

                        if (urlWebService == getroleinfo)
                            loadRoleInfo(s);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            protected String doInBackground(Void... voids) {


                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }


        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }



    private void loadRoles(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        RolesAdapter rolesAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        String[] role_s = new String[jsonArray.length()];
        String[] id_s = new String[jsonArray.length()];

        ArrayList<Roles> roles = new ArrayList<Roles>();

        if (jsonArray.length() > 0) {
            allroles.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
                role_s[i] = obj.getString("role");
                id_s[i] = obj.getString("id");
              allroles.add(new Roleclass(role_s[i], id_s[i]));
                roles.add(new Roles(role_s[i], id_s[i]) );


            }
            rolesAdapter = new RolesAdapter(this, roles, this);
            roleslist.setAdapter(rolesAdapter);
            for (int i=0;i<allroles.size();i++) Log.d ("allroles", allroles.get(i).name);


        }


    }

    private void loadPercs(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        PercsAdapter percsAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        String[] perc_s = new String[jsonArray.length()];
        String[] id_s = new String[jsonArray.length()];
        String[] descr_s = new String[jsonArray.length()];
        String[] sid_s = new String[jsonArray.length()];

        ArrayList<Percs> percs = new ArrayList<Percs>();

        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
                perc_s[i] = obj.getString("perc");
                id_s[i] = obj.getString("id");
                descr_s[i]=obj.getString("status");
                sid_s[i]=obj.getString("stat_id");

percs.add(new Percs(perc_s[i], descr_s[i], id_s[i],sid_s[i]) );
diff_percs[i]=perc_s[i];
perc_status[i]=sid_s[i];
         }
            percsAdapter = new PercsAdapter(this, percs, this);
            percslist.setAdapter(percsAdapter);
            updatebutton.setVisibility(GONE);
            percname.setText("");
            opt1.setTextColor(getResources().getColor(R.color.cyberblue));
            opt2.setTextColor(getResources().getColor(R.color.cyberblue));
            opt3.setTextColor(getResources().getColor(R.color.cyberblue));

            options=0;
            getPercID="";

        }


    }



    private void loadLink(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        CharsAdapter charsAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        if (jsonArray.length() > 0) {


            JSONObject obj = jsonArray.getJSONObject(0);
            String isDesc = obj.getString("desc");
            if (isDesc.contains("#"))
            {
                String [] split=isDesc.split("#");
                if (split[1].contains("http")){
                Intent browserIntent = new

                Intent(Intent.ACTION_VIEW, Uri.parse(split[1]));
                startActivity(browserIntent);}
            }



        }
    }
    private void loadDescription(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        CharsAdapter charsAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        if (jsonArray.length() > 0) {


                JSONObject obj = jsonArray.getJSONObject(0);
            String isDesc = obj.getString("desc");
            showDescriptionDialog(isDesc,getCharID, false,"");

        }
    }
    private void loadJSON(String json) throws JSONException
    {
        JSONArray jsonArray = new JSONArray(json);
        if (jsonArray.length()==0) {}
        if (jsonArray.length()>0)
        {
            for (int i=0;i<jsonArray.length();i++)
            {JSONObject obj = jsonArray.getJSONObject(i);
            }
        }
    }


    private void loadRoleColumns(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        CharsAdapter charsAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        if (jsonArray.length() > 0) {
            RoleColumns.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
                RoleColumns.add(obj.getString("field"));
                Log.d("RoleColumn", RoleColumns.get(i));
            }
        }
    }

    private void loadFields(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        CharsAdapter charsAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        if (jsonArray.length() > 0) {
            fields.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
              fields.add(obj.getString("field"));
            }
        }
    }

    private void loadOnlyPerson(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        CharsAdapter charsAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        String[] name_s = new String[jsonArray.length()];
        String[] role_s = new String[jsonArray.length()];
        String[] class_s = new String[jsonArray.length()];
        String[] id_s = new String[jsonArray.length()];
        String[] timings_s = new String[jsonArray.length()];



        if (jsonArray.length() > 0) {
            chars.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
                name_s[i] = obj.getString("name");
                id_s[i] = obj.getString("id");
                class_s[i]=obj.getString("class");
                role_s[i]=obj.getString("role");
                timings_s[i]=obj.getString("timing");
                if (class_s[i].equals("null")) class_s[i]="Фракция не определена";
                if (role_s[i].equals("null")) role_s[i]="Роль не определена";

               chars.add(new Chars(name_s[i], role_s[i], class_s[i],id_s[i],timings_s[i] ));
                playersLists.add(new PlayersList(name_s[i], role_s[i], class_s[i],id_s[i]) );


            }
            charsAdapter = new CharsAdapter(this, chars, this);
            charslist.setAdapter(charsAdapter);
            userslist.setAdapter(charsAdapter);

        }


    }

    private void loadChat(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        MasterChatAdapter chatAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        String[] id_s = new String[jsonArray.length()];
        String[] message_s = new String[jsonArray.length()];
        String[] rlists_s = new String[jsonArray.length()];
        String[] time_s = new String[jsonArray.length()];
        String timeRec="";


        if (jsonArray.length() > 0) {
            mChat.clear();
            for (int i = jsonArray.length()-1; i >-1 ; i--) {
//
                JSONObject obj = jsonArray.getJSONObject(i);

                id_s[i] = obj.getString("id");
                message_s[i]=obj.getString("message");
                rlists_s[i]=obj.getString("rlist");
                time_s[i]=obj.getString("time");
                timeRec = time_s[i]+". To: "+rlists_s[i];


                mChat.add (new MasterChat(id_s[i], message_s[i], timeRec ));
              //  playersLists.add(new PlayersList(name_s[i], role_s[i], class_s[i],id_s[i]) );


            }
            chatAdapter = new MasterChatAdapter(this, mChat, this);
            masterchat.setAdapter(chatAdapter);
            playerchat.setAdapter(chatAdapter);

        }


    }

    private void loadCharInfo(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<DescChars> descchar = new ArrayList<DescChars>();
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        if (jsonArray.length() > 0) {
            JSONObject obj = jsonArray.getJSONObject(0);
            meaning[0]  = obj.getString("id");
            meaning[1] = obj.getString("name");
            meaning[2] = obj.getString("password");
            meaning[3] = obj.getString("class");
            meaning[4] = obj.getString("role");
            meaning[5] = obj.getString("description");
            meaning[6] = obj.getString("perc1");
            meaning[7] = obj.getString("perc2");
            meaning[8] = obj.getString("perc3");
            meaning[9] = obj.getString("perc4");
            meaning[10] = obj.getString("perc5");
            getCharID=obj.getString("id");
            String [] split=new String[]{};

            for (int i=0;i<meaning.length-1;i++)
            {String res=meaning[i+1];
            sOut(meaning[i]);
                if (res.contains("#"))
                {
                    split=res.split("#");
                    res=split[0];
                }
                if (res.length()>30) res=res.substring(0,25)+"...";
                if (i<5)descchar.add(new DescChars(cont_percs[i], res));
                else descchar.add(new DescChars(diff_percs[i-5], res));

            }
            for (int j=0;j<descchar.size();j++)
            {
            }
            DescCharsAdapter descCharsAdapter = new DescCharsAdapter(this,descchar,this);


            desccharslist.setAdapter(descCharsAdapter);
        }
    }

    private void loadRoleInfo(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<RoleInfo> _roleinfo = new ArrayList<RoleInfo>();
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        if (jsonArray.length() > 0) {
            JSONObject obj = jsonArray.getJSONObject(0);
            for (int j=1;j<RoleColumns.size();j++) {
                String isField=obj.getString("field"+(j+1));
                String fieldname;


                if (j==3)
                {

                   getDescription = isField;
                    if (getDescription.contains("#"))
                    {
                        String [] splitting=getDescription.split("#");
                      //  splitting=getDescription.split("#");

                    isField = splitting[0].substring(0,25)+"...";}
                    else
                    {
                        if (getDescription.length()>22)
                        isField=getDescription.substring(0,22)+"...";
                        else
                            isField=getDescription;

                    }
                }


                if (j<4) fieldname=cont_roles[j-1];
                else fieldname=diff_percs[j-4];
                   _roleinfo.add(new RoleInfo(fieldname, isField, RoleColumns.get(j), obj.getString("field1")));
                if (j == 3)
                    class_id=RoleColumns.get(j);




                Log.d("Roleinfo", isField);

            }

            RoleInfoAdapter descCharsAdapter = new RoleInfoAdapter(this,_roleinfo,this);


           roleinfo.setAdapter(descCharsAdapter);

//            for (int i = 0; i < m; i++) {
////
//                JSONObject obj = jsonArray.getJSONObject(i);
//                name_s[i] = obj.getString("name");
//                id_s[i] = obj.getString("id");
//                class_s[i]=obj.getString("class");
//                role_s[i]=obj.getString("role");
//
//                chars.add(new Chars(name_s[i], role_s[i], class_s[i],id_s[i]) );
//
//
//            }


        }


    }

    private void loadRes(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ClassesAdapter classesAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }

        if (jsonArray.length() > 0) {

//
                JSONObject obj = jsonArray.getJSONObject(0);
                getReceiverResourse = obj.getString("res");


        }

    }

    private void loadClasses(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ClassesAdapter classesAdapter;
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        String[] class_s = new String[jsonArray.length()];
        String[] id_s = new String[jsonArray.length()];

        ArrayList<Classes> classes = new ArrayList<Classes>();

        if (jsonArray.length() > 0) {
            allclasses.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
                class_s[i] = obj.getString("class");
                id_s[i] = obj.getString("id");
                classes.add(new Classes(class_s[i], id_s[i]) );
                allclasses.add(new Roleclass(class_s[i], id_s[i]));

            }
            classesAdapter=new ClassesAdapter(this, classes,this);
            classlist.setAdapter(classesAdapter);
            fractionlist.setAdapter(classesAdapter);


        }


    }

    private void loadStatus(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        if (jsonArray.length() == 0) { Log.d ("data", "No data");
            return;
        }
        String take = "";
        String[] status_s = new String[jsonArray.length()];


        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
                status_s[i] = obj.getString("status");
                if (i==0)
                {
                    opt1.setText(status_s[i]);
                }
                if (i==1)
                {
                    opt2.setText(status_s[i]);
                }
                if (i==2)
                {
                    opt3.setText(status_s[i]);
                }

            }


        }


    }


    private void loadMaster(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        if (jsonArray.length() == 0) {
            sendToast("Имя пользователя или пароль не опознаны");

            return;
        }

        if (jsonArray.length() > 0) {
            caseMaster();
            dialog.dismiss();

            }
        }

    private void loadPlayerFull(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        if (jsonArray.length() == 0) {
            sendToast("Имя пользователя или пароль не опознаны");

            return;
        }

        if (jsonArray.length() > 0) {
            casePlayer();
            personfull.clear();
            JSONObject obj = jsonArray.getJSONObject(0);
            String [] alpercs={"","","","",""};
            alpercs[0]=getDecimal(obj.getString("perc1") );
            alpercs[1]=getDecimal(obj.getString("perc2"));
            alpercs[2]=getDecimal(obj.getString("perc3"));
            alpercs[3]=getDecimal(obj.getString("perc4"));
            alpercs[4]=getDecimal(obj.getString("perc5"));



            for (int i=0;i<alpercs.length;i++)
            {
                personfull.add(new PersonFull(diff_percs[i], alpercs[i],perc_status[i] ));

            }
            PersonFullAdapter personFullAdapter=new PersonFullAdapter(this,personfull,this);
            personfullinfo.setAdapter(personFullAdapter);
            getChat="http://a0568345.xsph.ru/gorynych/getchat.php/get.php?nom="+"\""+
            database.getString(ID,"")+"\"&dp=f"+database.getString(FRACTION,"");
            getJSON(getChat);
            playerLL.setVisibility(View.VISIBLE);
            if (dialog!=null) dialog.dismiss();

        }
    }

    private void loadPlayer(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        if (jsonArray.length() == 0) {
            sendToast("Имя пользователя или пароль не опознаны");

            return;
        }

        if (jsonArray.length() > 0) {
            casePlayer();
            personsmall.clear();
            JSONObject obj = jsonArray.getJSONObject(0);
            SharedPreferences.Editor editor = database.edit();
            editor.putString(ID, obj.getString("id"));
            editor.putString(PASS, obj.getString("pass"));
            editor.putString(NAME,obj.getString("name"));
            editor.putString(FRACTION,obj.getString("cid"));
            editor.apply();
            cont_player[0]=obj.getString("name");
            cont_player[1]=obj.getString("description");
            cont_player[2]=obj.getString("role");
            cont_player[3]=obj.getString("class");
            if (cont_player[1].length()>25) cont_player[1]=cont_player[1].substring(0,25)+"...";
            if (cont_player[2].equals("null")) cont_player[2] = "Роль не определена";
            if (cont_player[3].equals("null")) cont_player[3] = "Фракция не определена";

            for (int i=0;i<cont_persons.length;i++)
            {
                personsmall.add(new PersonSmall(cont_persons[i], cont_player[i]));

            }
            PersonSmallAdapter personSmallAdapter=new PersonSmallAdapter(this,personsmall,this);
            personsmallinfo.setAdapter(personSmallAdapter);
            getplayerfull="http://a0568345.xsph.ru/gorynych/getplayerfull.php/get.php?nom="
                    +database.getString(NAME,"")+"&dp="+database.getString(PASS,"");
            getJSON(getplayerfull);
            playerLL.setVisibility(View.VISIBLE);
            if (dialog!=null) dialog.dismiss();
            bottomLL.setVisibility(View.VISIBLE);

            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);
            DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String timeText = timeFormat.format(currentDate);
            new uploadAsyncTask().execute("updatechar", "timing",timeText ,database.getString(ID,"") ,"");
        }
    }


    public void casePlayer()
    {
        SharedPreferences.Editor editor = database.edit();
        editor.putString(ROLE,"player");
        editor.apply();

    }

    public void caseMaster()
    {
        SharedPreferences.Editor editor = database.edit();
        editor.putString(ROLE,"master");
        editor.apply();
        Animation fadein = AnimationUtils.loadAnimation(context, R.anim.fadein);
        masterLL.setVisibility(View.VISIBLE);
        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                Log.d("Fadein", "animstart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
bottomLL.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        masterLL.setAnimation(fadein);

    }
    public void sendToast(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    private class uploadAsyncTask extends AsyncTask<String, Integer, Double> {
        @Override
        protected Double doInBackground(String... params) {
            postData(params[0], params[1], params[2], params[3], params[4]);
            return null;
        }

        protected void onPostExecute(Double result) {
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        public void postData(String isUri, String post1, String post2, String post3, String post4) {
            HttpClient httpclient = new DefaultHttpClient();
            String getUri = String.format(DOMAIN+ "%s.php", isUri);
            HttpPost httppost = new HttpPost(getUri);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("post1", post1));
                nameValuePairs.add(new BasicNameValuePair("post2", post2));
                nameValuePairs.add(new BasicNameValuePair("post3", post3));
                nameValuePairs.add(new BasicNameValuePair("post4", post4));
//                nameValuePairs.add(new BasicNameValuePair("event",event ));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                HttpResponse response = httpclient.execute(httppost);
                sOut(getUri+", "+post1+", "+post2+", "+post3);
                if (getUri.contains("addrole"))
                {
                    getJSON(getroles);
                }

                if (getUri.contains("delrole"))
                {
                    getJSON(getroles);
                }

                if (getUri.contains("addclass"))
                {
                    getJSON(getclasses);
                }
                if (getUri.contains("delclass"))
                {
                    getJSON(getclasses);
                }

                if (getUri.contains("updateperc"))
                {

                    getJSON(getstatus);
                    getJSON(getpercs);
                }
                if (getUri.contains("updatechar") && database.getString(ROLE,"").equals("player"))
                {
                    getplayerfull="http://a0568345.xsph.ru/gorynych/getplayerfull.php/get.php?nom="
                            +database.getString(NAME,"")+"&dp="+database.getString(PASS,"");
                    getJSON(getplayerfull);
                }

                if (getUri.contains("deletemessage"))
                {
                    getChat="http://a0568345.xsph.ru/gorynych/getchat.php/get.php?nom="+"\""+"&dp=f";
                    getJSON(getChat);
                }

//                if (getUri.contains("updaterole") && post4.equals("update"))
//                {
//                    new uploadAsyncTask().execute("updateplayerfromrole", getCharID, post2,"","");
//                }




            } catch (ClientProtocolException e) {

            } catch (IOException e) {
            }

        }
    }

    public void sOut(String message)
    {
        System.out.println(message);
    }

    public String removelast(String str, int symb) {
        if (str != null && str.length() > symb) {
            str = str.substring(0, str.length() - symb);
        }
        return str;
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {


            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (database.getString(ROLE,"").equals("player")) {
                        ArrayList<FieldStatus> fstatus = new ArrayList<FieldStatus>();
                        for (int i = 0; i < perc_status.length; i++) {

                            String getData = personfull.get(i).percdata;
                            int intData=Integer.parseInt(getData);
                            String getID = database.getString(ID,"");
                            if (perc_status[i].equals("3") && intData>0) {
                                new uploadAsyncTask().execute("updatechar", fields.get(i+6),
                                        ""+(intData-1),getID,"");


                            }
                        }
                    }
                }
            });
        }

    }
    MediaPlayer isPlayer = new MediaPlayer();
    int [] tracks=new int[3];
    int currentTrack=0;
//    public void audio()
//
//    {
//        String isDomain="http://a0568345.xsph.ru/gorynych/audio/";
//
//        tracks[0] = R.raw.track1;
//        tracks[1] = R.raw.track2;
//        tracks[2] = R.raw.track3;
//        isPlayer = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
//        isPlayer.start();
//        isPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer arg0) {
//                arg0.release();
//                if (currentTrack <= tracks.length) {
//                    currentTrack++;
//                    if (currentTrack >= tracks.length) currentTrack = 0;
//                    arg0 = MediaPlayer.create(getApplicationContext(), tracks[currentTrack]);
//                    arg0.setOnCompletionListener(this);
//                    arg0.start();
//                }
//            }
//        });
//
//
//        }
String decimal="";
public String getDecimal(String input)
{
    //Получили количество триад
    int decimals=input.length()/3;
    int first=0;
    //создали массив для хранения триад
    ArrayList<String> dec = new ArrayList<>();

    decimal=""+decimals;
    first=input.length()-decimals*3;
    int mass = decimals*3;
    if (first>0)
    {
       dec.add(input.substring(0,first));
       decimal=input.substring(first,input.length());
    }
    else decimal=input;
    if (decimal.length()==3)
    {
        dec.add(decimal);
    }
    if (decimal.length()==6)
    {
        dec.add(decimal.substring(0,3));
        dec.add(decimal.substring(3));

    }
    if (decimal.length()==9)
    {
        dec.add(decimal.substring(0,3));
        dec.add(decimal.substring(3,6));
        dec.add(decimal.substring(6));

    }



    String _decimal="";
for (int i=0;i<dec.size();i++)
    {
        _decimal+=dec.get(i)+" ";
    }
decimal=_decimal;
return decimal;
}



}