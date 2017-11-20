package com.doganoguz.santra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> xmlList=new ArrayList<String>();
    ArrayList<String> xmlLink=new ArrayList<String>();




    public class arkaPlanIsleri extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);




        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            ListView listView1 = (ListView)findViewById(R.id.list);
            MyCustomAdapter adapter = new MyCustomAdapter(MainActivity.this, R.layout.list, xmlList);
            listView1.setAdapter(adapter);





            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialog.setMessage("Haberler Yükleniyor...");

            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            xmlList=getListFromXml("http://www.aspor.com.tr/rss/anasayfa.xml");
            xmlLink=getLinkFromXml("http://www.aspor.com.tr/rss/anasayfa.xml");
            return null;
        }

    }

    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> xmlList) {
            super(context, textViewResourceId, xmlList);
            // TODO Auto-generated constructor stub

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);

            View row = convertView;


            if(row==null){
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.list, parent, false);
            }

            TextView label=(TextView)row.findViewById(R.id.text1);
            label.setText(xmlList.get(position));

            ImageView image =(ImageView)row.findViewById(R.id.img);
            image.setImageResource(R.drawable.icon);

            return row;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Haberler Güncel.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5201112211038371/6696178447");






        new arkaPlanIsleri().execute();
        ListView listView1 = (ListView)findViewById(R.id.list);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Uri link = Uri.parse(xmlLink.get(position));

                final Intent openBrowser = new Intent(Intent.ACTION_VIEW, link);

                startActivity(openBrowser);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ayarlar.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, fikstur.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, puand.class);
            startActivity(intent);
        }
        else if (id == R.id.canli) {
            Intent intent = new Intent(this, canli.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_send) {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else if(id == R.id.nav_share)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Hakkında");
            builder.setMessage("Santra: v1.0.0.0                                       Geliştirici: Doğan OĞUZ");



            builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {


                }
            });


            builder.show();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public ArrayList<String> getListFromXml(String strng)  {

        ArrayList<String> list=new ArrayList<String>();

        try {

            URL url=new URL(strng);
            DocumentBuilderFactory dFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder=dFactory.newDocumentBuilder();

            Document document=dBuilder.parse(new InputSource(url.openStream()));
            document.getDocumentElement().normalize();

            NodeList nodeListCountry=document.getElementsByTagName("item");
            for (int i = 0; i < nodeListCountry.getLength(); i++) {
                Node node=nodeListCountry.item(i);
                Element elementMain=(Element) node;

                NodeList nodeListText=elementMain.getElementsByTagName("title");
                Element elementText=(Element) nodeListText.item(0);

                list.add(elementText.getChildNodes().item(0).getNodeValue());


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }

    public ArrayList<String> getLinkFromXml(String strng)  {

        ArrayList<String> list=new ArrayList<String>();

        try {

            URL url=new URL(strng);
            DocumentBuilderFactory dFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder=dFactory.newDocumentBuilder();

            Document document=dBuilder.parse(new InputSource(url.openStream()));
            document.getDocumentElement().normalize();

            NodeList nodeListCountry=document.getElementsByTagName("item");
            for (int i = 0; i < nodeListCountry.getLength(); i++) {
                Node node=nodeListCountry.item(i);
                Element elementMain=(Element) node;

                NodeList nodeListText=elementMain.getElementsByTagName("link");
                Element elementText=(Element) nodeListText.item(0);

                list.add(elementText.getChildNodes().item(0).getNodeValue());


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }
}
