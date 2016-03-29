package adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newagesmb.customgridcalendarlibrary.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import modal.Day;

/**
 * Created by livin on 3/29/2016.
 */
public class CustomCalendarAdapter extends BaseAdapter {
   String[] days;
   Context context;
   public ArrayList<Day> dayList=new ArrayList<>();
   List<Boolean> event_status;
   static final  int FIRST_DAY_OF_WEEK = 0;
   Calendar cal;
   public CustomCalendarAdapter(Context context, ArrayList<Day> dayList,List<Boolean> _event_status, Calendar cal ) {
      this.context = context;
      this.dayList = dayList;
      this.event_status=_event_status;
      this.cal = cal;
      refreshDays();
   }

   @Override
   public int getCount() {
      return dayList.size();
   }

   @Override
   public Object getItem(int position) {
      return dayList.get(position);
   }

   @Override
   public long getItemId(int position) {
      return 0;
   }


   @Override
   public View getView(final int position, View convertView, ViewGroup parent) {
      View v = convertView;
      LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      if (position >= 0 && position < 7) {
         v = vi.inflate(R.layout.day_of_week, null);
         TextView day = (TextView) v.findViewById(R.id.tv_new);
         if (position == 0) {
            day.setText(R.string.sunday);
         } else if (position == 1) {
            day.setText(R.string.monday);
         } else if (position == 2) {
            day.setText(R.string.tuesday);
         } else if (position == 3) {
            day.setText(R.string.wednesday);
         } else if (position == 4) {
            day.setText(R.string.thursday);
         } else if (position == 5) {
            day.setText(R.string.friday);
         } else if (position == 6) {
            day.setText(R.string.saturday);
         }
      } else {
         v = vi.inflate(R.layout.day_view, null);
         Day dayObject = dayList.get(position);
         RelativeLayout rl_outerView = (RelativeLayout) v.findViewById(R.id.rl);
         FrameLayout today = (FrameLayout) v.findViewById(R.id.today_frame);
         FrameLayout event = (FrameLayout) v.findViewById(R.id.event_frame);
         TextView tv_date = (TextView) v.findViewById(R.id.tv_new);
         Calendar local_cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

         tv_date.setVisibility(View.VISIBLE);
         rl_outerView.setVisibility(View.VISIBLE);

         Log.e("isEvent_status",dayObject.isEvent_status()+"");

         if (dayObject.isEvent_status()) {
            event.setVisibility(View.VISIBLE);
            today.setVisibility(View.GONE);
            tv_date.setTextColor(Color.BLACK);
         } else {
            event.setVisibility(View.GONE);
            tv_date.setTextColor(Color.BLACK);
         }

         if (dayObject.getYear() == local_cal.get(Calendar.YEAR) && dayObject.getMonth() == local_cal.get(Calendar.MONTH)
               && dayObject.getDay() == local_cal.get(Calendar.DAY_OF_MONTH)) {
            today.setVisibility(View.VISIBLE);
            tv_date.setTextColor(Color.WHITE);
         } else {
            today.setVisibility(View.GONE);
         }

         if (dayObject.getDay() == 0) {
            rl_outerView.setVisibility(View.GONE);
         } else {
            tv_date.setVisibility(View.VISIBLE);
            tv_date.setText(String.valueOf(dayObject.getDay()));
         }
      }

      return v;
   }

   public void refreshDays()
   {
      // clear items
      dayList.clear();

      int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)+7;
      int firstDay = (int)cal.get(Calendar.DAY_OF_WEEK);
      int year = cal.get(Calendar.YEAR);
      int month = cal.get(Calendar.MONTH);
      //TimeZone tz = TimeZone.getDefault();

      // figure size of the array
      if(firstDay==0){
         days = new String[lastDay+(FIRST_DAY_OF_WEEK*6)];
      }
      else {
         days = new String[lastDay+firstDay-(FIRST_DAY_OF_WEEK+1)];
      }

      int j=FIRST_DAY_OF_WEEK;

      // populate empty days before first real day
      if(firstDay>0) {
         for(j=0;j<(firstDay-FIRST_DAY_OF_WEEK)+7;j++) {
            days[j] = "";
            Day d = new Day(0,0,0,false);
            dayList.add(d);
         }
      }
      else {
         for(j=0;j<(FIRST_DAY_OF_WEEK*6)+7;j++) {
            days[j] = "";
            Day d = new Day(0,0,0,false);
            dayList.add(d);
         }
         j=FIRST_DAY_OF_WEEK*6+1; // sunday => 1, monday => 7
      }

      // populate days
      int dayNumber = 1;

      if(j>0 && dayList.size() > 0 && j != 1){
         dayList.remove(j-1);
      }

      for(int x=0, i=j-1;i<days.length;i++) {
         Day d;
         if (event_status.size()>x) {
            d = new Day(dayNumber,year,month,event_status.get(x));
         }else {
            d = new Day(dayNumber,year,month,false);
         }
         x++;

         Calendar cTemp = Calendar.getInstance();
         cTemp.set(year, month, dayNumber);
         //int startDay = Time.getJulianDay(cTemp.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cTemp.getTimeInMillis())));



         days[i] = ""+dayNumber;
         dayNumber++;
         dayList.add(d);
      }

   }
}
