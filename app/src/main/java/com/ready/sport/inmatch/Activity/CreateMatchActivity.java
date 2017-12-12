package com.ready.sport.inmatch.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.DoubleDateAndTimePickerDialog;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.MatchModel;
import com.ready.sport.inmatch.RealmClass.PlayerCardMatchModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.Tools.CustomAdaptersPlayersMatch;
import com.ready.sport.inmatch.util.AdapterInterface;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.EditTextPlus;
import com.ready.sport.inmatch.util.EditTextTint;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.shawnlin.numberpicker.NumberPicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateMatchActivity extends AppCompatActivity implements AdapterInterface , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, NumberPicker.OnValueChangeListener {
    private AppBarLayout bar;
    private TextViewPlus title;
    private TextViewPlus numSelectPl;
    private EditTextPlus dataText;
    private EditTextPlus locationText;
    private EditTextPlus team1Text;
    private EditTextPlus team2Text;
    private TextViewPlus numTotPl;
    private NumberPicker numberPicker;
    private int colorSet;
    private int count = 0;
    private String locationStr;
    private String dataStr;
    private String team1Str;
    private String team2Str;
    private int type;
    private String dataToSave;

    // Date picker 1
    private SingleDateAndTimePickerDialog.Builder singleBuilder;
    private SimpleDateFormat simpleDateFormat;


    // Date picker 2


    private Realm realm;

    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    private static RealmResults<PlayersModel> data;
    private ArrayList<Integer> listaPlayer = new ArrayList<>();
    private static ArrayList<PlayerCardMatchModel> dataFin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        bar = (AppBarLayout)findViewById(R.id.appbarCreateMatch);
        title = (TextViewPlus)findViewById(R.id.titleNewMatch);
        numSelectPl = (TextViewPlus)findViewById(R.id.numberSelectPlayer);
        dataText = (EditTextPlus)findViewById(R.id.dataNewMatch);
        locationText = (EditTextPlus)findViewById(R.id.locationNewMatch);
        team1Text = (EditTextPlus)findViewById(R.id.team1NewMatch);
        team2Text = (EditTextPlus)findViewById(R.id.team2NewMatch);
        numberPicker = (NumberPicker)findViewById(R.id.number_picker);
        numTotPl = (TextViewPlus)findViewById(R.id.numberTotPlayer);



        numberPicker.setOnValueChangedListener(this);

        numberPicker.setValue(1);

        numTotPl.setText("2");

        // Date picker 1
        simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());


        dataText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //simpleClicked();
                datePicker();
            }
        });

        try {
            EditTextTint.applyColor(dataText, Color.parseColor("#a3a3a3"));
            EditTextTint.applyColor(locationText, Color.parseColor("#a3a3a3"));
            EditTextTint.applyColor(team1Text, Color.parseColor("#a3a3a3"));
            EditTextTint.applyColor(team2Text, Color.parseColor("#a3a3a3"));
        } catch (EditTextTint.EditTextTintError e) {
            e.printStackTrace();
        }


        Bundle extras = getIntent().getExtras();

        try {
            if(extras == null) {
                type= Constants.SOCCER_TYPE;
            } else {
                type= extras.getInt(Constants.MATCH_TYPE);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            type= Constants.SOCCER_TYPE;
        }
        setColorBar(type);
        AppCompatImageView back = (AppCompatImageView)findViewById(R.id.backBtnCreateMatch);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        AppCompatImageView add = (AppCompatImageView)findViewById(R.id.add_match_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        NestedScrollView scroller = (NestedScrollView) findViewById(R.id.scrollNewMatch);

        realm= Realm.getDefaultInstance();

        recyclerView = (RecyclerView) findViewById(R.id.listPlayerNewMatch);
        //recyclerView.setHasFixedSize(true);
        dataFin = new ArrayList<PlayerCardMatchModel>();
        data = realm.where(PlayersModel.class).findAll();
        for (PlayersModel pl : data) {
            PlayerCardMatchModel plFin = new PlayerCardMatchModel();
            plFin.MatchType = type;
            plFin.PlayerId = pl.getIdPlayer();
            plFin.NamePlayer = pl.getName();
            plFin.SurnamePlayer = pl.getSurName();
            if(type == Constants.SOCCER_TYPE){
                plFin.RatingPlayer = pl.getRatingSoccer();
                plFin.RoleSoccer = "CENTROVAMPISTA";
            }else if(type == Constants.BASKET_TYPE){
                plFin.RatingPlayer = pl.getRatingBasket();
            }else if(type == Constants.TENNIS_TYPE){
                plFin.RatingPlayer = pl.getRatingTennis();
            }else if(type == Constants.VOLLEY_TYPE){
                plFin.RatingPlayer = pl.getRatingVolley();
            }
            dataFin.add(plFin);
        }
        setUpRecyclerView();
    }

    private void validate() {

        // Reset errors.
        locationText.setError(null);
        dataText.setError(null);
        team1Text.setError(null);
        team2Text.setError(null);

        // Store values at the time of the login attempt.
        locationStr = locationText.getText().toString();
        dataStr = dataText.getText().toString();
        team1Str = team1Text.getText().toString();
        team2Str = team2Text.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!cancel && TextUtils.isEmpty(locationStr)) {
            Toast.makeText(this, getString(R.string.error_location_match), Toast.LENGTH_SHORT).show();
            //name.setError(getString(R.string.error_name_player));
            focusView = locationText;
            cancel = true;
        }
        if (!cancel && TextUtils.isEmpty(dataStr)) {
            Toast.makeText(this, getString(R.string.error_data_match), Toast.LENGTH_SHORT).show();
            //name.setError(getString(R.string.error_name_player));
            focusView = dataText;
            cancel = true;
        }
        if (!cancel && TextUtils.isEmpty(team1Str)) {
            Toast.makeText(this, getString(R.string.error_first_team_match), Toast.LENGTH_SHORT).show();
            //name.setError(getString(R.string.error_name_player));
            focusView = team1Text;
            cancel = true;
        }
        if (!cancel && TextUtils.isEmpty(team2Str)) {
            Toast.makeText(this, getString(R.string.error_second_team_match), Toast.LENGTH_SHORT).show();
            //name.setError(getString(R.string.error_name_player));
            focusView = team2Text;
            cancel = true;
        }

        if(!cancel && Integer.parseInt(numTotPl.getText().toString()) != count){
            Toast.makeText(this, getString(R.string.error_second_team_match).replace("{0}",numTotPl.getText().toString()), Toast.LENGTH_SHORT).show();
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            createMatch();
        }
    }

    public void setColorBar(int index){

        switch (index){
            case Constants.SOCCER_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.soccerColor));
                colorSet = getResources().getColor(R.color.soccerColor);
                title.setText("Nuova partita - Calcio");
                return;
            case Constants.BASKET_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.basketColor));
                colorSet = getResources().getColor(R.color.basketColor);
                title.setText("Nuova partita - Basket");
                return;
            case Constants.TENNIS_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.tennisColor));
                colorSet = getResources().getColor(R.color.tennisColor);
                title.setText("Nuova partita - Tennis");
                return;
            case Constants.VOLLEY_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.volleyColor));
                colorSet = getResources().getColor(R.color.volleyColor);
                title.setText("Nuova partita - Volley");
                return;
        }
    }

    private void setUpRecyclerView() {
        adapter = new CustomAdaptersPlayersMatch(this,dataFin);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        /*TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);*/

    }

    // Date picker 1 method
    public void simpleClicked() {

        final Date now = new Date();
        final Calendar calendarMin = Calendar.getInstance();
        final Calendar calendarMax = Calendar.getInstance();

        calendarMin.setTime(now); // Set min now
        calendarMax.setTime(new Date(now.getTime() + TimeUnit.DAYS.toMillis(150))); // Set max now + 150 days

        final Date minDate = calendarMin.getTime();
        final Date maxDate = calendarMax.getTime();

        singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)

                .backgroundColor(getResources().getColor(R.color.separatorColor))
                .mainColor(colorSet)
                .minutesStep(15)
                .mustBeOnFuture()

                .minDateRange(minDate)
                .maxDateRange(maxDate)
                .defaultDate(now)
                .title("Double")

                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        dataText.setText(simpleDateFormat.format(date));
                    }
                });
        singleBuilder.display();
    }

    // Date picker 2 method

    public void datePicker(){
        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(colorSet);
        dpd.setMinDate(now);
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dataText.setText("");
            }
        });
    }

    public void timePicker(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        dpd.setVersion(TimePickerDialog.Version.VERSION_2);
        dpd.setTimeInterval(1,5,60);
        dpd.setAccentColor(colorSet);
        dpd.show(getFragmentManager(), "Timepickerdialog");
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dataText.setText("");
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(newVal*2 < count){
            numberPicker.setValue(oldVal);
            numTotPl.setText(String.valueOf(oldVal*2));
            Toast.makeText(this,"Deseleziona alcuni giocatori", Toast.LENGTH_SHORT).show();
        }else{
            numTotPl.setText(String.valueOf(newVal*2));
        }

    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    @Override
    public void setPlayerForMatch(int idPlayer,int type){
        if(type == 0){
            count = count-1;
            listaPlayer.remove(new Integer(idPlayer));
        }else{
            count = count+1;
            listaPlayer.add(new Integer(idPlayer));
        }
        numSelectPl.setText(String.valueOf(count));
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String day = "";
        if(String.valueOf(dayOfMonth).length() == 1)day = "0"+dayOfMonth;
        String date = day +"/"+(++monthOfYear)+"/"+year;
        dataText.setText(date);
        dataToSave = year + "/"+(monthOfYear)+"/"+day+" ";
        timePicker();
    }
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        String time = hourString+":"+minuteString;
        dataText.setText(dataText.getText() +" " + time);
        dataToSave = dataToSave+time+":00.503";
    }

    public void createMatch(){
        String listaPlayerStr = listaPlayer.get(0).toString()+"_";
        for(int i = 1;i<listaPlayer.size();i++){
            listaPlayerStr += "_"+listaPlayer.get(i).toString();
        }

        final MatchModel model = new MatchModel();
        model.setStartDateUtc(dataToSave);
        model.setNameFirstTeam(team1Str);
        model.setNameSecondTeam(team2Str);
        model.setLocation(locationStr);
        model.setIsFinish(false);
        model.setMatchType(type);
        model.setListTotalPlayers(listaPlayerStr);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    realm.copyToRealmOrUpdate(model);
                } catch (Exception e) {
                    Log.e("TAG", "ADD_MATCH: " + e.getMessage(), e);
                } finally {
                    Log.d("TAG", "ADD_MATCH: FINALLY");
                    Toast.makeText(getBaseContext(), "Operazione eseguita", Toast.LENGTH_SHORT).show();
                }
            }
        });
        realm.close();
        finish();
    }

    public int numberPlayerRemain(){
        return Integer.parseInt(numTotPl.getText().toString()) - count;
    }
}


