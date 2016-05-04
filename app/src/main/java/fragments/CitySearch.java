package fragments;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import database.DataBase;
import maxalexan.ru.project.R;

public class CitySearch extends Fragment{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private SQLiteDatabase mSqLiteDatabase;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String LOG_TAG = "my_log";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public CitySearch() {
        // Required empty public constructor
    }

    public static CitySearch newInstance(String param1, String param2) {
        CitySearch fragment = new CitySearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // получаем данные с внешнего ресурса
    }
   protected View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText serchingCity = (EditText) getActivity().findViewById(R.id.search_edit_frame);
            if (serchingCity.getText().toString().equals("")){
                Toast.makeText(getActivity(), "EMPTY CITY", Toast.LENGTH_SHORT).show();
            }
            else{
                new ParseTask(serchingCity.getText().toString()).execute();
              }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_search, container, false);
        Button buttonSearchCity = (Button) view.findViewById(R.id.search_button);
        buttonSearchCity.setOnClickListener(onClickListener);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
private class ParseTask extends AsyncTask<Void, Void, String> {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";
    String city = "";
    public ParseTask(String serchingCity) {
        this.city = serchingCity;
    }

    @Override
    protected String doInBackground(Void... params) {
           try {
            URL url = new URL(getResources().getString(R.string.url_city_search_API)+this.city+"&"+getResources().getString(R.string.url_apiKey_search_API));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
            buffer.append(line);
            }
            resultJson = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }
    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        JSONObject dataJsonObj = null;
        String cityName = "";
        try {
            dataJsonObj = new JSONObject(strJson);
            JSONObject aboutCity = dataJsonObj.optJSONObject("city");
            String name = aboutCity.getString("name");
            String country = aboutCity.getString("country");
            JSONArray  aboutList = dataJsonObj.getJSONArray("list");
            JSONObject parametr = aboutList.getJSONObject(0);
            String dateTameValue = parametr.getString("dt_txt");
            JSONObject clouds = parametr.getJSONObject("clouds");
            JSONObject degree = parametr.getJSONObject("main");
            String cloudsValue = clouds.getString("all");
            String degreeValue = degree.getString("temp");
            String humidityValue = degree.getString("humidity");

            TextView text_information_country  = (TextView) getActivity().findViewById(R.id.text_information_country);
            TextView text_information_city  = (TextView) getActivity().findViewById(R.id.text_information_city);
            TextView text_information_data  = (TextView) getActivity().findViewById(R.id.text_information_data);
            TextView text_information_time  = (TextView) getActivity().findViewById(R.id.text_information_time);
            TextView text_information_clouds  = (TextView) getActivity().findViewById(R.id.text_information_clouds);
            TextView text_information_degree  = (TextView) getActivity().findViewById(R.id.text_information_degree);
            TextView text_information_wet  = (TextView) getActivity().findViewById(R.id.text_information_wet);
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            SimpleDateFormat newTimeFormat = new SimpleDateFormat("EEEE kk:mm:ss", Locale.getDefault());
            Date dateDate = null,dateTime = null;
            try {
                dateDate = oldDateFormat.parse(dateTameValue.toString());
                dateTime = oldDateFormat.parse(dateTameValue.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String changeFormatDateValue = newDateFormat.format(dateDate);
            String changeFormatTimeValue = newTimeFormat.format(dateTime);

            text_information_country.setText(getResources().getText(R.string.data_country).toString()+":"+ country);
            text_information_city.setText(getResources().getText(R.string.data_city).toString()+":"+ name);
            text_information_data.setText(getResources().getText(R.string.data_date).toString()+":"+ changeFormatDateValue);
            text_information_time.setText(getResources().getText(R.string.data_time).toString()+":"+ changeFormatTimeValue);
            text_information_clouds.setText(getResources().getText(R.string.data_cloud).toString()+":"+ cloudsValue);
            text_information_degree.setText(getResources().getText(R.string.data_temperature).toString()+":"+ degreeValue +"K");
            text_information_wet.setText(getResources().getText(R.string.data_wet).toString()+":"+ humidityValue);
            try {

                DataBase mDatabaseHelper = new DataBase(getActivity(), "cities.db", null, 1);
                mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(mDatabaseHelper.NAME_CITI,name);
                values.put(mDatabaseHelper.NAME_COUNTRY,country);
                values.put(mDatabaseHelper.DATA_DATE,changeFormatDateValue);
                values.put(mDatabaseHelper.DATA_TIME,changeFormatTimeValue);
                values.put(mDatabaseHelper.CLOUDS_VALUE,cloudsValue);
                values.put(mDatabaseHelper.DEGREE_VALUE, degreeValue + "K");
                values.put(mDatabaseHelper.HUMIDITY_VALUE,humidityValue);
                mSqLiteDatabase.insert("cities", null, values);

                Toast.makeText(getActivity(),"Recorded successfully", Toast.LENGTH_LONG).show();
            }
            catch(Throwable t)
            {
                Toast.makeText(getActivity(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public DataBase getDataBase() {
        return new DataBase(getActivity());
    }
}
}
