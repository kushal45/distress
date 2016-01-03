package notifactionspecial.com.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;


/**
 * Created by USER on 07-Dec-15.
 */
public class MyDialog extends DialogFragment {
String items[]={"Home","Work"};
Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;

    }

    @NonNull

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Select group to send SMS to:-");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


  communicator.Sendmessage(which);

            }

        });
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Cancel was clicked",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Ok was clicked",Toast.LENGTH_SHORT).show();
            }
        });
      Dialog dialog=builder.create();
        return dialog;
    }




}
interface  Communicator
{
    void  Sendmessage(int which);
}