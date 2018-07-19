package com.obsez.android.lib.filechooser.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.obsez.android.lib.filechooser.tool.DirAdapter;

import java.io.File;


/**
 * A placeholder fragment containing a simple view.
 */
public class ChooseFileActivityFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ChooseFileActivityFragment";
    private String _path;
    private TextView _tv;
    private ImageView _iv;

    private final static int READ_PERMS_REQUEST = 0;

    public ChooseFileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choose_file, container, false);
        _tv = (TextView) root.findViewById(R.id.textView);
        _tv.setText(BuildConfig.VERSION_NAME);
        _iv = (ImageView) root.findViewById(R.id.imageView);
        root.findViewById(R.id.btn_choose_a_file).setOnClickListener(this);
        root.findViewById(R.id.btn_choose_a_folder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // choose a folder
                final Context ctx = getActivity();
                try {
                    new ChooserDialog().with(ctx)
                            .withIcon(R.mipmap.ic_hedzr_logo)
                            .withFilter(true, false)
                            .withStartFile(_path)
                            .withDateFormat("HH:mm")
                            .withResources(R.string.title_choose_folder, R.string.title_choose,
                                    R.string.dialog_cancel)
                            //.withOnCancelListener(new DialogInterface.OnCancelListener(){
                            //
                            //    /**
                            //     * This method will be invoked when the dialog is canceled.
                            //     *
                            //     * @param dialog the dialog that was canceled will be passed into the
                            //     *               method
                            //     */
                            //    @Override
                            //    public void onCancel(DialogInterface dialog) {
                            //        Log.d("CANCEL", "CANCEL");
                            //    }
                            //})
                            //.withNegativeButtonListener(new DialogInterface.OnClickListener(){
                            //
                            //    /**
                            //     * This method will be invoked when a button in the dialog is clicked.
                            //     *
                            //     * @param dialog the dialog that received the click
                            //     * @param which  the button that was clicked (ex.
                            //     *               {@link DialogInterface#BUTTON_POSITIVE}) or the position
                            //     */
                            //    @Override
                            //    public void onClick(DialogInterface dialog, int which) {
                            //        Log.d("Negative", "Negative");
                            //    }
                            //})
                            .withChosenListener(new ChooserDialog.Result() {
                                @Override
                                public void onChoosePath(String path, File pathFile) {
                                    Toast.makeText(ctx, "FOLDER: " + path, Toast.LENGTH_SHORT).show();
                                    _path = path;
                                    _tv.setText(_path);
                                }
                            })
                            .build()
                            .show(READ_PERMS_REQUEST);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        root.findViewById(R.id.btn_choose_any_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context ctx = getActivity();
                try {
                    new ChooserDialog(ctx)
                            .withStartFile(_path)
                            .withResources(R.string.title_choose_any_file, R.string.title_choose, R.string.dialog_cancel)
                            .withFileIconsRes(false, R.mipmap.ic_my_file, R.mipmap.ic_my_folder)
                            .withAdapterSetter(new ChooserDialog.AdapterSetter() {
                                @Override
                                public void apply(DirAdapter adapter) {
                                    //
                                }
                            })
                            .withChosenListener(new ChooserDialog.Result() {
                                @Override
                                public void onChoosePath(String path, File pathFile) {
                                    Toast.makeText(ctx, "FILE: " + path, Toast.LENGTH_SHORT).show();

                                    _path = path;
                                    _tv.setText(_path);
                                    //_iv.setImageURI(Uri.fromFile(pathFile));
                                    _iv.setImageBitmap(ImageUtil.decodeFile(pathFile));
                                }
                            })
                            .build()
                            .show(READ_PERMS_REQUEST);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        //choose a file
        final Context ctx = this.getActivity();
        try {
            new ChooserDialog(ctx)
                    .withFilterRegex(false, true, ".*\\.(jpe?g|png)")
                    .withStartFile(_path)
                    .withResources(R.string.title_choose_file, R.string.title_choose, R.string.dialog_cancel)
                    .withChosenListener(new ChooserDialog.Result() {
                        @Override
                        public void onChoosePath(String path, File pathFile) {
                            Toast.makeText(ctx, "FILE: " + path, Toast.LENGTH_SHORT).show();

                            _path = path;
                            _tv.setText(_path);
                            //_iv.setImageURI(Uri.fromFile(pathFile));
                            _iv.setImageBitmap(ImageUtil.decodeFile(pathFile));
                        }
                    })
                    .withNavigateUpTo(new ChooserDialog.CanNavigateUp() {
                        @Override
                        public boolean canUpTo(File dir) {
                            return true;
                        }
                    })
                    .withNavigateTo(new ChooserDialog.CanNavigateTo() {
                        @Override
                        public boolean canNavigate(File dir) {
                            return true;
                        }
                    })
                    .build()
                    .show(READ_PERMS_REQUEST);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
