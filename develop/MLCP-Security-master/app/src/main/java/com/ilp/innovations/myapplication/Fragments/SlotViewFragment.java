package com.ilp.innovations.myapplication.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class SlotViewFragment extends Fragment{

    private ProgressDialog pDialog;
    private ListView slotList;
    private ArrayList<Slot> slots;
    private SlotAdapter slotAdapter;
    public boolean searchBySlot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Toast.makeText(getActivity().getApplication(),"asd",Toast.LENGTH_SHORT).show();
    }


    public SlotViewFragment newInstance() {
        SlotViewFragment fragment = new SlotViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setTitle("Please wait");
        pDialog.setMessage("Loading slot list");
        showDialog();

        slotList = (ListView) rootView.findViewById(R.id.slotList);
        slots = new ArrayList<>();
        slotAdapter = new SlotAdapter(slots);
        slotList.setAdapter(slotAdapter);

listner();
        slotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Slot selectedSlot = slots.get(position);
                Toast.makeText(getActivity().getApplicationContext(),String.valueOf(selectedSlot.isAllocated()),Toast.LENGTH_SHORT).show();
                if(selectedSlot.isAllocated()) {
                    //todo alertdialog for release slot
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Release Slot");
                    builder.setMessage("Do you really want to release this slot?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //todo release the slot here
                            showDialog();
                            Intent releaseIntent = new Intent(getActivity(),BookingUpdaterService.class);
                            releaseIntent.setAction(BookingUpdaterService.ACTION_CLEAR_SLOT);
                            releaseIntent.putExtra("slotId",selectedSlot.getSlotId());
                            getActivity().startService(releaseIntent);
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
                else {
                    //todo alertdialog for reserve slot
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Reserve Slot");
                    builder.setMessage("Do you really want to reserve this slot?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //todo reserve the slot here
                            showDialog();
                            new reserveslot(getActivity()).execute(selectedSlot.getSlotId());
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
            }
        });

        Intent intent = new Intent(getActivity(),BookingUpdaterService.class);
        intent.setAction(BookingUpdaterService.ACTION_GET_ALL_SLOTS);
        getActivity().startService(intent);

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BookingUpdaterService.BROADCAST_ACTION_GET_ALL_SLOTS);
        filter.addAction(BookingUpdaterService.BROADCAST_ACTION_CLEAR_SLOT);
        filter.addAction(BookingUpdaterService.BROADCAST_ACTION_RESERVE_SLOT);
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

        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.search_head).setVisible(false);

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
                Intent intent = new Intent(getActivity(),BookingUpdaterService.class);
                intent.setAction(BookingUpdaterService.ACTION_GET_ALL_SLOTS);
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
            String response = intent.getStringExtra("response");
            Log.d("dataaaaaaaaaaaaaa",response);
            hideDialog();
            if(response!=null) {
                String action = intent.getAction();
                if (action.equals(BookingUpdaterService.BROADCAST_ACTION_GET_ALL_SLOTS)) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if (!error) {
                            slots.clear();
                            slotAdapter = new SlotAdapter(slots);
                            slotList.setAdapter(slotAdapter);
                            JSONArray data = jObj.getJSONArray("values");
                            for (int i = 0; i < data.length(); i++) {

                                JSONObject item = data.getJSONObject(i);
                                if(BookingUpdaterService.LEVEL_ID==Integer.parseInt(item.getString("floorid")))
                                {
                                    Slot slot = new Slot();
                                    String slotId = item.getString("slotid");
                                    slot.setSlotId(slotId);
                                    slot.setSlot(item.getString("slotid"));
                                    //   slot.setSlot(item.getString("slotname"));
                                    if (item.getString("status").equals("FREE")) {
                                        slot.setIsAllocated(false);
                                    } else {
                                        slot.setIsAllocated(true);
                                    }
                                if (item.getString("isReserved").equals("YES")) {
                                    slot.setIsReserved(true);
                                } else {
                                    slot.setIsReserved(false);
                                }

                                    //slot.setIsReserved(false);

                                    slots.add(slot);
                                }
                            }
                            slotAdapter.notifyDataSetChanged();
                            hideDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(action.equals(BookingUpdaterService.BROADCAST_ACTION_CLEAR_SLOT) ||
                        action.equals(BookingUpdaterService.BROADCAST_ACTION_RESERVE_SLOT)) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if (!error) {
                            slots.clear();
                            showDialog();
                            Intent updateIntent = new Intent(getActivity(), BookingUpdaterService.class);
                            updateIntent.setAction(BookingUpdaterService.ACTION_GET_ALL_SLOTS);
                            getActivity().startService(updateIntent);
                        }
                        else {
                            Toast.makeText(getActivity(), jObj.getString("errorMsg"),
                                Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
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
                    intent.setAction(BookingUpdaterService.ACTION_GET_ALL_SLOTS);
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
                    dialogButton.setOnClickListener(new View.OnClickListener() {
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
            Slot slot = getItem(position);
            holder.slotId.setText("Slot ID:"+String.valueOf(slot.getSlotId()));
            holder.slotName.setText(slot.getSlot());
            holder.img.setVisibility(View.GONE);
            //if(slot.isAllocated()) {
            //    holder.slotId.setTextColor(Color.GRAY);
           // }
            return convertView;
        }

        class ViewHolder {
            private TextView slotId;
            private TextView slotName;
            private ImageView img;

            ViewHolder(View view) {
                slotName = (TextView) view.findViewById(R.id.reg_id);
                slotId = (TextView) view.findViewById(R.id.slot);
                img = (ImageView) view.findViewById(R.id.avtar);
                view.setTag(this);
            }
        }
    }
}
