package cz.krtinec.root1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;

public class TestActivity extends Activity {
    private static final int ACTIVITY_PICK_CONTACT = 42;
    private static final int DIALOG_SHOW_CONTACT = 10;
    private static final int MENU_FIND_CONTACT = 1;

    private static Uri pickedContact;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    private void pickContact() {
      Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
      startActivityForResult(intent, ACTIVITY_PICK_CONTACT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_FIND_CONTACT, 0, "Vyber kontakt").setIcon(android.R.drawable.ic_menu_add);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_FIND_CONTACT: {
                pickContact();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
       switch (requestCode) {
          case (ACTIVITY_PICK_CONTACT) :
              if (resultCode == Activity.RESULT_OK) {
                  //hotovo, máme kontakt
                  pickedContact = data.getData();
                  showDialog(DIALOG_SHOW_CONTACT);
                  return;
              }
          break;
      }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_SHOW_CONTACT: {
                return new AlertDialog.Builder(this).
                        setTitle("URI kontaktu").
                        setMessage("Message").
                        setCancelable(true).
                        setPositiveButton("OK", null).
                        create();
            }
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DIALOG_SHOW_CONTACT: {
                if (pickedContact != null) {
                    ((AlertDialog)dialog).setMessage(pickedContact.toString());
                }
            }
        }
    }

}
