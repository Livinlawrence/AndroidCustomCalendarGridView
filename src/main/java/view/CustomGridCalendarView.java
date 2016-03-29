package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newagesmb.customgridcalendarlibrary.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import adapter.CustomCalendarAdapter;
import modal.Day;

/**
 * Created by livin on 3/29/2016.
 */
public class CustomGridCalendarView extends LinearLayout implements AdapterView.OnItemClickListener, View.OnClickListener {

   private Context mContext;
   private View view;
   ImageButton ib_back, ib_next;
   GridView grid_calender;
   private OnDayClickListener dayListener;
   private CustomCalendarAdapter mAdapter;
   private Calendar cal;
   private TextView tv_month;
   public ArrayList<Day> dayList = new ArrayList<Day>();
   List<Boolean> event_status = new ArrayList<>();

   public interface OnDayClickListener {
      public void onDayClicked(AdapterView<?> adapter, View view,
                               int position, long id, Day day);
   }

   public CustomGridCalendarView(Context context) {
      super(context, null);

   }

   public CustomGridCalendarView(Context context, AttributeSet attrs) {
      super(context, attrs);
      this.mContext = context;
      initializeCalendar();
   }

   private void initializeCalendar() {
      final LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflate.inflate(R.layout.layout_calendar_view, this, true);
      ib_back = (ImageButton) view.findViewById(R.id.img_back);
      ib_next = (ImageButton) view.findViewById(R.id.img_next);
      grid_calender = (GridView) view.findViewById(R.id.grid_calender);
      tv_month = (TextView) view.findViewById(R.id.txt_month);
      ib_back.setOnClickListener(this);
      ib_next.setOnClickListener(this);


      cal = Calendar.getInstance();
      tv_month.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
            Locale.getDefault())
            + " " + cal.get(Calendar.YEAR));
      rebuildCalendarAdapter();
   }


   private void rebuildCalendarAdapter(){
      mAdapter = new CustomCalendarAdapter(mContext, dayList, event_status, cal);
      grid_calender.setAdapter(mAdapter);
      grid_calender.setSelector(android.R.color.transparent);
      grid_calender.setVerticalSpacing(4);
      grid_calender.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
      grid_calender.setDrawSelectorOnTop(true);
      mAdapter.notifyDataSetChanged();
   }


   @Override
   public void onClick(View v) {
      if (v.getId() == R.id.img_back) {
         previousMonth();
      } else if (v.getId() == R.id.img_next) {
         nextMonth();
      }

}

   @Override
   public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
      if (dayListener != null) {
         Day d = (Day) mAdapter.getItem(arg2);
         if (d.getDay() != 0) {
            dayListener.onDayClicked(arg0, arg1, arg2, arg3, d);
         }
      }
   }

   public void setOnDayClickListener(OnDayClickListener listener) {
      if (grid_calender != null) {
         dayListener = listener;
         grid_calender.setOnItemClickListener(this);
      }
   }

   private void previousMonth() {
      if (cal.get(Calendar.MONTH) == cal.getActualMinimum(Calendar.MONTH)) {
         cal.set((cal.get(Calendar.YEAR) - 1),
               cal.getActualMaximum(Calendar.MONTH), 1);
      } else {
         cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
      }
      rebuildCalendar();
   }

   private void nextMonth() {
      if (cal.get(Calendar.MONTH) == cal.getActualMaximum(Calendar.MONTH)) {
         cal.set((cal.get(Calendar.YEAR) + 1),
               cal.getActualMinimum(Calendar.MONTH), 1);
      } else {
         cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
      }
      rebuildCalendar();
   }


   private void rebuildCalendar() {
      if (tv_month != null) {
         tv_month.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
               Locale.getDefault()) + " " + cal.get(Calendar.YEAR));
        rebuildCalendarAdapter();
      }
   }




   /**
    * Accept the list as event status list with the size of 31
    *
    * @param event_status_list
    */
   public void setEvent_statusList(List<Boolean> event_status_list) {
      event_status = event_status_list;
      rebuildCalendarAdapter();
   }


}
