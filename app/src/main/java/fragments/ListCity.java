package fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import database.CitiInformation;
import database.Communicator;
import database.DataBase;
import maxalexan.ru.project.R;

public class ListCity extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ArrayList<String> listDialog;
    final String LOG_TAG = "myLogs";
    private ArrayAdapter<String> adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private List<String> list;
    private List<CitiInformation> citiInformationList;
    private Cursor cursor;
    private SQLiteDatabase mSqLiteDatabase;
    private Communicator mListener;
    private CitiInformation cityInformation;
    public ListCity() {
        list =  new ArrayList<>();
        citiInformationList = new ArrayList<>();
    }
    public static ListCity newInstance(String param1, String param2) {
        ListCity fragment = new ListCity();
        listDialog = new ArrayList<String>();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Запустился onCreate ");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_city, container, false);
        listView = (ListView) view.findViewById(R.id.simple_list);
        new DataTask().execute();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityListItemDialog dialogFragment = new CityListItemDialog();
                Bundle args = new Bundle();
                try {
                    listDialog = (ArrayList<String>) citiInformationList.get(position).getMassiv();
                    args.putStringArrayList(ARG_PARAM1, listDialog);
                    dialogFragment.setArguments(args);
                    dialogFragment.show(getFragmentManager(), "dialogFragment");
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
            listView.setAdapter(adapter);
        }
                // TODO: Rename method, update argument and hook method into UI event

        @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG, "Запустился onDetach ");
        mListener = null;
    }

    private class DataTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Log.d(LOG_TAG, "Запустился THREAD ");
                DataBase mDatabaseHelper = new DataBase(getActivity(), "cities.db", null, 1);
                mSqLiteDatabase = mDatabaseHelper.getReadableDatabase();
                cursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + DataBase.TABLE, null);
                list.clear();
                citiInformationList.clear();
                int indexId = cursor.getColumnIndex(DataBase._ID);
                int indexCity = cursor.getColumnIndex(DataBase.NAME_CITI);
                int indexCountry = cursor.getColumnIndex(DataBase.NAME_COUNTRY);
                int indexDataDate = cursor.getColumnIndex(DataBase.DATA_DATE);
                int indexDataTime = cursor.getColumnIndex(DataBase.DATA_TIME);
                int indexClouds = cursor.getColumnIndex(DataBase.CLOUDS_VALUE);
                int indexDegree = cursor.getColumnIndex(DataBase.DEGREE_VALUE);
                int indexHumidity = cursor.getColumnIndex(DataBase.HUMIDITY_VALUE);
                while(cursor.moveToNext()){
                   cityInformation = new CitiInformation(cursor.getInt(indexId),cursor.getString(indexCity),cursor.getString(indexCountry),cursor.getString(indexDataDate),cursor.getString(indexDataTime),cursor.getString(indexClouds),cursor.getString(indexDegree),cursor.getString(indexHumidity));
                   citiInformationList.add(cityInformation);
                   list.add(cityInformation.getDegree());
                }
                citiInformationList.add(cityInformation);
            } catch (Throwable t) {
                Toast.makeText(getActivity(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
            return list;
        }
        @Override
        protected void onPostExecute(List<String> strJson) {
            super.onPostExecute(strJson);
        }
        public DataBase getDataBase() {
            return new DataBase(getActivity());
        }
    }
}


