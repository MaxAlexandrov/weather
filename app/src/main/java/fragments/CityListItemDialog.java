package fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import database.CitiInformation;
import maxalexan.ru.project.R;


public class CityListItemDialog extends DialogFragment implements OnClickListener{
    final String LOG_TAG = "myLogs";
    private  List<String> list;
    private  int param;
    private CitiInformation citiInformation;
    private static final String ARG_PARAM1 = "param1";
    DialogFragment dialogFragment;
    private Cursor cursor;
    private SimpleCursorAdapter userAdapter;
    private SQLiteDatabase mSqLiteDatabase;


    public CityListItemDialog() {
        // Required empty public constructor
        list = new ArrayList<String>();
            }

    public static CityListItemDialog newInstance( CitiInformation city ) {
        CityListItemDialog fragment = new CityListItemDialog();
      // args.putStringArrayList(param1,list);
       // fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getStringArrayList(ARG_PARAM1);
           Log.d(LOG_TAG, "Пришло: " + list);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getResources().getString(R.string.information));
        View view = inflater.inflate(R.layout.dialog_list_city, null);
        TextView dialog_information_country  = (TextView) view.findViewById(R.id.dialog_information_country);
        TextView dialog_information_city  = (TextView) view.findViewById(R.id.dialog_information_city);
        TextView dialog_information_data  = (TextView) view.findViewById(R.id.dialog_information_data);
        TextView dialog_information_time  = (TextView) view.findViewById(R.id.dialog_information_time);
        TextView dialog_information_clouds  = (TextView) view.findViewById(R.id.dialog_information_clouds);
        TextView dialog_information_degree  = (TextView) view.findViewById(R.id.dialog_information_degree);
        TextView dialog_information_wet  = (TextView) view.findViewById(R.id.dialog_information_wet);
        dialog_information_country.setText(getResources().getText(R.string.data_country).toString() + ":"+ list.get(1));
        dialog_information_city.setText(getResources().getText(R.string.data_city).toString()+":"+ list.get(0));
        dialog_information_data.setText(getResources().getText(R.string.data_date).toString()+":"+ list.get(2));
        dialog_information_time.setText(getResources().getText(R.string.data_time).toString()+":"+ list.get(3));
        dialog_information_clouds.setText(getResources().getText(R.string.data_cloud).toString()+":"+ list.get(4));
        dialog_information_degree.setText(getResources().getText(R.string.data_temperature).toString() + ":" + list.get(5) + "K");
        dialog_information_wet.setText(getResources().getText(R.string.data_wet).toString() + ":" + list.get(6));
        view.findViewById(R.id.btnYes).setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {
        Log.d(LOG_TAG, "Dialog : " + ((Button) v).getText());
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
   }
}
