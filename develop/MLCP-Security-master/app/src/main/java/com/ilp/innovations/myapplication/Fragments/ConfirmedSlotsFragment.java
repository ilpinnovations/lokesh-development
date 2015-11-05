package com.ilp.innovations.myapplication.Fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ilp.innovations.myapplication.Beans.Slot;
import com.ilp.innovations.myapplication.R;
import com.ilp.innovations.myapplication.Services.BookingUpdaterService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class ConfirmedSlotsFragment extends Fragment{

    private ProgressDialog pDialog;
    private ListView slotList;
    private ArrayList<Slot> slots;
    private SlotAdapter slotAdapter;

    public boolean searchBySlot;
    private SearchManager searchManager;
    private SearchView searchView;


    public ConfirmedSlotsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public Fragment newInstance() {
        ConfirmedSlotsFragment fragment = new ConfirmedSlotsFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setTitle("Please wait");
        pDialog.setMessage("Loading slots list confirmed");
        showDialog();
listner();
        slotList = (ListView) rootView.findViewById(R.id.slotList);
        slots = new ArrayList<>();
        slotAdapter = new SlotAdapter(slots);
        slotList.setAdapter(slotAdapter);

        slotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //todo alertdialog for release slot
                final Slot selectedSlot = slots.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Unallocate Slot");
                builder.setMessage("Do you really want to unallocate this slot?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //todo release the slot here
                        //showDialog();
                        slots.clear();
                        new clearslot(getActivity()).execute(selectedSlot.getSlot());


                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Intent intent = new Intent(getActivity(),BookingUpdaterService.class);
        intent.setAction(BookingUpdaterService.ACTION_GET_CONFIRMED_SLOTS);
        getActivity().startService(intent);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BookingUpdaterService.BROADCAST_ACTION_GET_CONFIRMED_SLOTS);
        filter.addAction(BookingUpdaterService.BROADCAST_ACTION_SWIPE_OUT);
        getActivity().registerReceiver(receiver, filter);
        Log.d("myTag", "Broadcast receiver registered");
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(receiver);
        Log.d("myTag", "Broadcast receiver unregistered");
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.menu_main, menu);

        if(searchBySlot) {
            menu.findItem(R.id.search_slot).setIcon(getResources().getDrawable(R.drawable.check));
            menu.findItem(R.id.search_reg_num).setIcon(null);
        }
        else {
            menu.findItem(R.id.search_reg_num).setIcon(getResources().getDrawable(R.drawable.check));
            menu.findItem(R.id.search_slot).setIcon(null);
        }

        searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setBackgroundColor(getResources().getColor(R.color.bg_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText==null || newText.length()==0)
                    return false;
                if (slots != null) {
                    ArrayList<Slot> updatedSlots = new ArrayList<Slot>();
                    for (Slot eachSlot : slots) {
                        if (searchBySlot && eachSlot.getSlot().contains(newText)) {
                            updatedSlots.add(eachSlot);
                        } else if (!searchBySlot && eachSlot.getRegId()!=null) {
                            if(eachSlot.getRegId().contains(newText))
                                updatedSlots.add(eachSlot);
                        }
                    }
                    slotAdapter = new SlotAdapter(updatedSlots);
                    slotList.setAdapter(slotAdapter);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search_slot:
                searchBySlot = true;
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.search_reg_num:
                searchBySlot = false;
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.action_refresh:
                showDialog();
                slots.clear();
                Intent intent = new Intent(getActivity(),BookingUpdaterService.class);
                intent.setAction(BookingUpdaterService.ACTION_GET_CONFIRMED_SLOTS);
                getActivity().startService(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("myTag", "11111Response--->" + "lokesh");
            String response = intent.getStringExtra("response");
            hideDialog();
            hideDialog();
            if(response!=null) {

                //Toast.makeText(getActivity().getApplicationContext(),"inconfirmd",Toast.LENGTH_SHORT).show();
                String action = intent.getAction();
                if (action.equals(BookingUpdaterService.BROADCAST_ACTION_GET_CONFIRMED_SLOTS)) {
                    try {
                        hideDialog();
                        Log.d("myTag", response);
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        // Check for error node in json
                        if (!error) {
                            JSONArray data = jObj.getJSONArray("values");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject item = data.getJSONObject(i);
                                Slot slot = new Slot();
                                slot.setBookId(Integer.parseInt(item.getString("booking_id")));
                                slot.setEmpId(item.getString("employeeid"));
                                slot.setRegId(item.getString("vehiclenumber"));
                                slot.setSlot(item.getString("slotid"));
                                slots.add(slot);

                            }
                            slotAdapter.notifyDataSetChanged();
                            hideDialog();
                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("errorMsg");
                            Toast.makeText(getActivity(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                        Toast.makeText(getActivity(),
                                "Error in response!",
                                Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException ne) {
                        Toast.makeText(getActivity(),
                                "Error in connection! Please check your connection",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }
            else {
                Toast.makeText(getActivity(),
                        "Error in connection. Either not connected to internet or wrong server addr",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private class SlotAdapter extends BaseAdapter {

        private ArrayList<Slot> adpaterList = new ArrayList<Slot>();

        public SlotAdapter(ArrayList<Slot> adapterList) {
            this.adpaterList = adapterList;
        }


        @Override
        public int getCount() {
            return this.adpaterList.size();
        }

        @Override
        public Slot getItem(int position) {
            return this.adpaterList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                //setting the view with list item
                convertView = View.inflate(getActivity(), R.layout.slot_item, null);

                // This class is necessary to identify the list item, in case convertView!=null
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            //getting view elements value from ArrayList

            //getting view elements value from ArrayList
            Slot currentSlot = getItem(position);
            String titleString = currentSlot.getRegId();
            String allocatedSlot = "Slot: " + currentSlot.getSlot();
            //setting the view element with corressponding value
            holder.regId.setText(titleString);
            holder.slot.setText(allocatedSlot);

            return convertView;
        }

        class ViewHolder {
            private TextView regId;
            private TextView slot;
            private ImageView img;

            ViewHolder(View view) {
                regId = (TextView) view.findViewById(R.id.reg_id);
                slot = (TextView) view.findViewById(R.id.slot);
                img = (ImageView) view.findViewById(R.id.avtar);
                view.setTag(this);
            }
        }
    }
    public static TextView result=null;
    void listner()
    {
        result=new TextView(getActivity());
        result.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                try
                {
                  hideDialog();
                    slots.clear();

                  slotAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(getActivity(),BookingUpdaterService.class);
                    intent.setAction(BookingUpdaterService.ACTION_GET_CONFIRMED_SLOTS);
                    getActivity().startService(intent);
                    showDialog();
                }
                catch(Exception e)
                {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.customerror);
                    dialog.setTitle("");

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(1, 26, 204, 194)));
                    // dialog.getWindow().setTitleColor(R.color.titlecolor);


                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.text);
                    text.setText("Internet Not Available");


                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                //	startActivity(i);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
    }
}
