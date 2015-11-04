package com.example.eandreje.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateDocumentFragment extends Fragment implements DefaultDialogFragment.DefaultDialogFragmentListener,
        OptionsDialogFragment.OptionsDialogFragmentListener{

    private static final String DIALOG_TITLE = "Lägg till nytt dokument";
    private static final String DOCLIST_TITLE = "Alternativ";

    private boolean state = false;
    private ArrayAdapter<DocItem> adapter;
    private List<DocItem> docList;
    private ListItem listItem;
    private ListView listView;
    private DocItem docClicked;
    private boolean importExport = false;
    String exportFile;
    public static final int requestcode =1;
    public CreateDocumentFragmentListener createDocumentFragmentListener;
    private OptionsDialogFragment optionsDialogFragment;
    private CSV csv;

    //newInstance factoring method, returns a new instance of this class
    // with custom parameter
    public static CreateDocumentFragment newInstance(ListItem listItem){
        CreateDocumentFragment fragment = new CreateDocumentFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("listobject", listItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        listItem = getArguments().getParcelable("listobject");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(listItem.name);
    }

    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_layout, container, false);
        docList = new ArrayList<>();
        docList = Queries.getDocuments(listItem);
        csv = new CSV();
        optionsDialogFragment = new OptionsDialogFragment();
        optionsDialogFragment.listener = this;

        listView = (ListView) view.findViewById(R.id.document_listview);
        adapter = new ArrayAdapter<>(getActivity(), R.layout.row_layout, docList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                docClicked = adapter.getItem(position);
                if (importExport)
                {
                    exportFile();
                    listView.setBackgroundColor(Color.WHITE);
                    importExport = false;
                }
                else
                createDocumentFragmentListener.docObjectClicked(docList.get(position));
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                docClicked = adapter.getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("DialogTitle", DOCLIST_TITLE);
                    bundle.putInt("Layout", R.layout.act_options_layout);
                    bundle.putInt("Caller", 0);
                    optionsDialogFragment.show(getFragmentManager(), "docOptions");
                    return true;
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try
        {
            createDocumentFragmentListener = (CreateDocumentFragmentListener)getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString() + " must implement CreateActivityFragmentListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.document_actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add_doc_icon:
                DefaultDialogFragment defaultDialogFragment = new DefaultDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", DIALOG_TITLE);
                bundle.putInt("Layout", R.layout.default_dialog);
                bundle.putInt("Caller", R.id.add_doc_icon);
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.listener = this;
                defaultDialogFragment.show(getFragmentManager(), "NewDocDialog");
                break;
            case R.id.second_view_up_cloud:
                listView.setBackgroundColor(Color.LTGRAY);
                importExport = true;
                Toast.makeText(getActivity(), "Välj ett dokument att exportera", Toast.LENGTH_SHORT).show();
                break;
            case R.id.second_view_down_cloud:
                importFile();
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void enteredText(String text, int id) {
        if(text.contentEquals("") || text.contains("\n"))
        {
            Toast.makeText(getActivity(), "Aktiviteten måste vara unik, ej innehålla mellanslag eller ny rad", Toast.LENGTH_LONG).show();
        }
        else
        if(!state)
        {
            addDocument(text);
        }
        else
        {
            docClicked.load(DocItem.class, docClicked.getId());
            docClicked.name = text;
            docClicked.save();
            UpdateAndSave();
            state = false;
        }
    }

    @Override
    public void enteredTextBool(String text, int caller, boolean checked) {

    }

    public void UpdateAndSave()
    {
        docList = Queries.getDocuments(listItem);
        adapter.clear();
        adapter.addAll(docList);
    }

    @Override
    public void getChoice(int pos) {
    switch (pos){
        case 0:
            state = true;
            DefaultDialogFragment docDialog = new DefaultDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("addDocTitle", DIALOG_TITLE);
            bundle.putInt("Layout", R.layout.default_dialog);
            docDialog.setArguments(bundle);
            docDialog.listener = this;
            docDialog.show(getFragmentManager(), "editDocDialog");
            break;
        case 1:
            docClicked.delete();
            UpdateAndSave();
            break;
        default:
    }
    }
    public void addDocument(String name)
    {
        DocItem document = new DocItem(name);
        document.parentActivity = listItem;
        document.save();
        UpdateAndSave();
    }

    @Override
    public void getDocChoice(DocItem doc) {

    }

    public interface CreateDocumentFragmentListener{
        void docObjectClicked(DocItem doc);
    }

    public void exportFile()
    {

        csv.writeToCSV(docClicked);
        exportFile = csv.getCSV();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, exportFile);
        startActivity(shareIntent);

    }
    public void importFile()
    {
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("text/plain");
        startActivityForResult(fileintent, requestcode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        String filepath = data.getData().getPath();
        File file = new File(filepath);
        String filename = file.getName();
        filename = filename.replace(".txt", "");
        addDocument(filename);


        Toast.makeText(getActivity(), filename+".txt imported", Toast.LENGTH_SHORT).show();


        //      TODO    Pröva att spara filen lokalt, eller ladda den direkt från dropbox.
        //        if (requestCode == 1) {
//                        try {
//                            InputStreamReader inputStreamReader = new InputStreamReader(filepath);
//                            //FileReader file = new FileReader(filepath)
//                            BufferedReader buffer = new BufferedReader(file);
//                            ContentValues values = new ContentValues();
//                            String line = "";
//                            while ((line = buffer.readLine()) != null) {
//                                String[] str = line.split(",", 3);
//                                String doc = str[0].toString();
//                                DocItem document = new DocItem(doc);
//                                document.save();
//                            }
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
        //FileReader file = new FileReader(filepath);
        //         }

        //      }

        //   }

    }
}
//fileName_share = adapter.getItem(position).name.toString();


//File file = new File(fileName_share+".txt");
//OUTPUTFILE_TEMP = fileName_share;
//                    try
//                    {
//                        FileOutputStream fos = getContext().openFileOutput(OUTPUTFILE_TEMP, getContext().MODE_PRIVATE);
//                        fos.write(exportTest.getBytes());
//                        fos.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }catch(IOException e){
//                        e.printStackTrace();
//                    }

//                  File sharefile = new File(getContext().getFilesDir(), "OUTPUTFILE_TEMP");
//                   sharefile.setReadable(true);
//                  Uri ShareUri = Uri.fromFile(sharefile);


//Uri uri = Uri.fromFile(new File(getContext().getFilesDir(), "OUTPUTFILE_TEMP"));
//File file = new File(getContext().getFilesDir() + "/"+ OUTPUTFILE_TEMP);

//                  shareIntent.putExtra(Intent.EXTRA_STREAM, ShareUri);
//                    shareIntent.putExtra(Intent.EXTRA_TITLE, OUTPUTFILE_TEMP);

//                    Intent chooseIntent = Intent.createChooser(shareIntent, "Share");

