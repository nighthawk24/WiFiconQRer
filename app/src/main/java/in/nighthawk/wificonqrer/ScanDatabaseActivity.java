package in.nighthawk.wificonqrer;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

import in.nighthawk.wificonqrer.Database.ScanDatabaseManager;
import in.nighthawk.wificonqrer.Models.Scan;
import in.nighthawk.wificonqrer.Network.HttpManager;

/**
 * Created by adi on 4/5/16.
 */
public class ScanDatabaseActivity extends ListActivity {

    private ScanDatabaseManager scanDataSource;

    private String[] scans = new String[]{"F56D2", "G95W1", "K77Y5"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_database);

        scanDataSource = new ScanDatabaseManager(this);
        scanDataSource.open();

        List<Scan> values = scanDataSource.getAllScans();

        ArrayAdapter<Scan> adapter = new ArrayAdapter<Scan>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Scan> adapter = (ArrayAdapter<Scan>) getListAdapter();
        Scan scan;
        switch (view.getId()) {

            case R.id.add:
                int nextInt = new Random().nextInt(3);
                // save the new scan to the database
                scan = scanDataSource.createScan(scans[nextInt]);
                adapter.add(scan);
                break;

            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    scan = (Scan) getListAdapter().getItem(0);
                    scanDataSource.deleteScan(scan);
                    adapter.remove(scan);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // TODO:
        HttpManager.sendScan(scanDataSource.getScan(id));
    }

    @Override
    protected void onResume() {
        scanDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        scanDataSource.close();
        super.onPause();
    }

}
