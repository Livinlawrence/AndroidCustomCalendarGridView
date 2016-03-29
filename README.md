# Custom CalendarGridView-Android
<img src="/images/screen1.png" width="270px" height="480px" />



This is a GridView customised to be used as a CalendarView. As the native 
Android CalendarView has limited functionalities, This customised GridView
will be helpful for you to highlight dates with your own style


##usage
	 <view.CustomGridCalendarView
	            android:id="@+id/grid_calender"
	            android:layout_width="match_parent"
	            android:layout_height="match_parent"/>
         
  #Adding onItemClickListner
  
  	grid_calender = (CustomGridCalendarView) _rootView.findViewById(R.id.grid_calender);
      	grid_calender.setOnDayClickListener(new CustomGridCalendarView.OnDayClickListener() {
         @Override
         public void onDayClicked(AdapterView<?> adapter, View view,
                                  int position, long id, Day day) {
            // TODO Auto-generated method stub
         }
	 });
  
  Highlighting the dates, now we do need to workaround on a boolean List of size 31. Dates which is to be highlighten can be passed as true.
  
	  	 List<Boolean> event_status = new ArrayList<>();
	  	 //Sample usage
		      for (int i=0;i<31;i++){
		         if (i%2==0){
		            event_status.add(true);
		         }else{
		            event_status.add(false);
		         }
		      }
		 grid_calender.setEvent_statusList(event_status);
  
      
      
   	This will be helpful if you are dealing with any webservice responds.
      
####License
 	Copyright 2015 Livin Lawrence

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
