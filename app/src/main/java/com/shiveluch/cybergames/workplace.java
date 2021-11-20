package com.shiveluch.cybergames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class workplace extends AppCompatActivity {
    SharedPreferences database;
    public final static String NAME="name";
    public final static  String PASS="pass";
    public final static String DESCRIPTION="description";
    public final static String ROLE="role";
    public static final String SET = "settings";
    public static final String DOMAIN="http://a0568345.xsph.ru/gorynych/";
LinearLayout startLL, playerLL, setuproles, setupclasses, setuppercs, setupusers;
RelativeLayout masterLL;
Button playerbut,masterbut, addrole, removerole, exitbutton, addclass, removeclass;
Context context;
boolean login=false;
ListView roleslist, classlist, percslist;
TextView users, roles, percs, classes, opt1, opt2, opt3;
String getroles="http://a0568345.xsph.ru/gorynych/getroles.php";
String getclasses="http://a0568345.xsph.ru/gorynych/getclasses.php";
String getstatus="http://a0568345.xsph.ru/gorynych/getstats.php";
String getpercs="http://a0568345.xsph.ru/gorynych/getpercs.php";
String getmaster;
EditText rolename, classname, percname;
String getRoleID="", getClassID="", getPercID="";
int options=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workplace);
        database=getSharedPreferences(SET, Context.MODE_PRIVATE);

        initVisualElements();
    }

    public void initVisualElements()

    {
        startLL=findViewById(R.id.startLL);
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
        rolename=findViewById(R.id.rolename);
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
        percslist=findViewById(R.id.percslist);
        percname=findViewById(R.id.percsname);

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


percslist.setOnItemClickListener((parent, view, position, id) -> {
    TextView isPerc=view.findViewById(R.id.a_perc);
    TextView sid=view.findViewById(R.id.p_lon);
    TextView pID=view.findViewById(R.id.a_id);
    String sPerc=isPerc.getText().toString();
    String sSid=sid.getText().toString();
    System.out.println(sSid);
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

        percs.setOnClickListener(v -> {
            setuproles.setVisibility(View.GONE);
            setupclasses.setVisibility(View.GONE);
            setupusers.setVisibility(View.GONE);
            setuppercs.setVisibility(View.VISIBLE);
            users.setTextColor(getResources().getColor(R.color.cyberblue));
            roles.setTextColor(getResources().getColor(R.color.cyberblue));
            percs.setTextColor(getResources().getColor(R.color.cybergreen));
            classes.setTextColor(getResources().getColor(R.color.cyberblue));
            getJSON(getstatus);
            getJSON(getpercs);


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
            }
        });

       // playerLL=findViewById(R.id.playerLL)

        roleslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView a_id=view.findViewById(R.id.a_id);
                TextView a_role = view.findViewById(R.id.a_role);
                rolename.setText(a_role.getText().toString());
                getRoleID = a_id.getText().toString();
                System.out.println(getRoleID);
            }
        });
addrole.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String _rolename=rolename.getText().toString();
        if(_rolename.length()>1)
            new uploadAsyncTask().execute("addrole",_rolename,"","","");

        else {sendToast("Недостаточная длина названия");return;}
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
                setupclasses.setVisibility(View.GONE);
                setupusers.setVisibility(View.GONE);
                setuppercs.setVisibility(View.GONE);
                users.setTextColor(getResources().getColor(R.color.cyberblue));
                roles.setTextColor(getResources().getColor(R.color.cybergreen));
                percs.setTextColor(getResources().getColor(R.color.cyberblue));
                classes.setTextColor(getResources().getColor(R.color.cyberblue));
                getJSON(getroles);

            }
        });

        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setuproles.setVisibility(View.GONE);
                setupclasses.setVisibility(View.VISIBLE);
                setupusers.setVisibility(View.GONE);
                setuppercs.setVisibility(View.GONE);
                users.setTextColor(getResources().getColor(R.color.cyberblue));
                roles.setTextColor(getResources().getColor(R.color.cyberblue));
                percs.setTextColor(getResources().getColor(R.color.cyberblue));
                classes.setTextColor(getResources().getColor(R.color.cybergreen));
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
startLL.setVisibility(View.GONE);
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
                        startLL.setVisibility(View.GONE);
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
                masterLL.setVisibility(View.GONE);
                startLL.setVisibility(View.VISIBLE);
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

                        if (urlWebService == getclasses)
                            loadClasses(s);

                        if (urlWebService == getstatus)
                            loadStatus(s);

                        if (urlWebService == getpercs)
                            loadPercs(s);



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
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
                role_s[i] = obj.getString("role");
                id_s[i] = obj.getString("id");



                roles.add(new Roles(role_s[i], id_s[i]) );


            }
            rolesAdapter = new RolesAdapter(this, roles, this);
            roleslist.setAdapter(rolesAdapter);
            rolename.setText("");

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


            }
            percsAdapter = new PercsAdapter(this, percs, this);
            percslist.setAdapter(percsAdapter);
            percname.setText("");

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
            for (int i = 0; i < jsonArray.length(); i++) {
//
                JSONObject obj = jsonArray.getJSONObject(i);
                class_s[i] = obj.getString("class");
                id_s[i] = obj.getString("id");



                classes.add(new Classes(class_s[i], id_s[i]) );


            }
            classesAdapter = new ClassesAdapter(this, classes, this);
            classlist.setAdapter(classesAdapter);
            classname.setText("");

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




            } catch (ClientProtocolException e) {

            } catch (IOException e) {
            }

        }
    }

}