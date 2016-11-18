package com.example.sammx343.probepokemongo;

        import android.*;
        import android.Manifest;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentSender;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.location.Location;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.net.Uri;
        import android.support.annotation.MainThread;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.common.api.PendingResult;
        import com.google.android.gms.common.api.ResultCallback;
        import com.google.android.gms.common.api.Status;
        import com.google.android.gms.location.LocationListener;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.location.LocationSettingsRequest;
        import com.google.android.gms.location.LocationSettingsResult;
        import com.google.android.gms.location.LocationSettingsStatusCodes;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import java.io.Serializable;
        import java.sql.SQLOutput;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;
        import java.util.concurrent.ExecutionException;

        import static java.lang.Math.acos;
        import static java.lang.Math.cos;

public class MapsActivity extends FragmentActivity implements Serializable, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, ResultCallback<LocationSettingsResult>, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISENCONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static final int MY_PERMISSION_REQUEST = 2;
    private GoogleMap mMap;
    private String TAG = "Mapa";
    List<Position> markers = new ArrayList<Position>();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    String ubicacion;
    List<Pokemon> pokes = new ArrayList<Pokemon>();
    Location locinit;
    private boolean primero = true;
    private double distance = 0;
    public static Pokemon pokemonWild;

    private GoogleApiClient googleapiclient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingRequest;
    private com.google.android.gms.location.LocationListener locationListener;
    private static final int REQUEST_CHECK_SETTINGS = 123;
    private boolean mPermissionDenied = false;
    private ProgressDialog pDialogo;
    Marker currLocationMarker;
    private Context context = this;
    ArrayList<Bitmap> bmImg = new ArrayList<Bitmap>();
    boolean first = true;

    List<Position> pokeparadas = new ArrayList<Position>();

    //pokeparadas = [(11.0195171,-74.851243),(11.0200334,-74.8512933),(11.0191992,-74.8505736),(11.0179433,-74.8504558),(10.9953985,-74.8278909)];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ProgressDialog pDialogo = new ProgressDialog(context);

        googleapiclient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).addApi(AppIndex.API).build();
        locationListener = this;
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISENCONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES);

        pokeparadas.add(new Position("11.0195171","-74.851243"));
        pokeparadas.add(new Position("11.0200334","-74.8512933"));
        pokeparadas.add(new Position("11.0191992","-74.8505736"));
        pokeparadas.add(new Position("11.0179433","-74.8504558"));
        pokeparadas.add(new Position("10.9953985","-74.8278909"));

        /*pokeparadas.add("11.0195171","-74.851243");
        pokeparadas.add("11.0200334","-74.8512933");
        pokeparadas.add("11.0191992","-74.8505736");
        pokeparadas.add("11.0179433","-74.8504558");
        pokeparadas.add("10.9953985","-74.8278909");*/
        //Intent i = getIntent();
        //pokes = (List<Pokemon>) i.getSerializableExtra("pokelist");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingRequest = builder.build();
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleapiclient, locationSettingRequest);
        result.setResultCallback(this);
        pokes = MainActivity.Pokemones;
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST);

            return;
        }


        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        for(int i=0; i<pokeparadas.size();i++){
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(pokeparadas.get(i).getLat()), Double.parseDouble(pokeparadas.get(i).getLn()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.pokestop)).title("pokeparada"));
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                double distance =  greatCircleInMeters(currLocationMarker.getPosition(), arg0.getPosition());
                if (distance < 50){
                        if(arg0.getTitle().equals("pokeparada")){
                            Intent i = new Intent(getApplicationContext(), pokestop.class);
                            startActivityForResult(i, 5);
                            finish();
                        }else {
                            int position = Integer.parseInt(arg0.getTitle());
                            pokemonWild = pokes.get(position);
                            Toast.makeText(MapsActivity.this, distance + " ", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), ChoosePokemon.class);

                            //                       System.out.println(pok.getName()+" "+pok.getAttack()+" "+" "+pok.getNumber()+" "+ pok.getTipo()+" "+pok.getImgFront()+" "+pok.getImgBack()+" "+pok.getMaxHp()+pok.getMaxAttack()+" "+pok.getMaxDefense()+" "+pok.getMaxSpeed()+pok.getEv_id());
                            //                       i.putExtra("Pokemon", pokes.get(1));
                            startActivityForResult(i, 4);
                            finish();
                        }

                    //}catch (Exception e){
                        //Toast.makeText(getApplicationContext(), "Ups, parece que esto no es un pokemon", Toast.LENGTH_SHORT).show();
                    //}

                }else {
                    Toast.makeText(MapsActivity.this, "Este pokemon se encuentra a "+distance+ " acercaté más!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

        });
        googleMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(19.0f));
        //Log.d(TAG, String.valueOf(pokes.size()));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        if (googleapiclient.isConnected()) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }

                            LocationServices.FusedLocationApi.requestLocationUpdates(googleapiclient, locationRequest, locationListener);
                        }
                        break;
                }

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        googleapiclient.connect();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.sammx343.probepokemongo/http/host/path")
        );
        AppIndex.AppIndexApi.start(googleapiclient, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.sammx343.probepokemongo/http/host/path")
        );
        AppIndex.AppIndexApi.end(googleapiclient, viewAction);
        googleapiclient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (googleapiclient.isConnected()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST);

                return;
            }
            Location lastlocation = LocationServices.FusedLocationApi.getLastLocation(googleapiclient);
            ubicacion = "http://190.144.171.172/function3.php?lat=" + lastlocation.getLatitude() + "&lng=" + lastlocation.getLongitude();
            try {
                UpdateMap(ubicacion);
                System.out.println("Esta aquí");
                getImage();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void getImage() throws ExecutionException, InterruptedException {
        for (int i = 0; i < pokes.size(); i++) {
            Bitmap myBitmap = new BitMapDeco(pDialogo, pokes.get(i).getImgFront()).execute().get();
            pokes.get(i).setBitmap(myBitmap);
        }
    }

    public void UpdateMap(String ubicacion) throws ExecutionException, InterruptedException {
        markers.clear();
        boolean verif = VerificarRed();
        if (verif == true) {
            pDialogo = new ProgressDialog(context);
            System.out.println("Este es la primera entrada con los puntos "+ubicacion);
            new PointsDecoder(pDialogo, (MapsActivity) context, markers, ubicacion).execute();
        } else {
            Toast.makeText(context, "No WiFi Conexion", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println(pokes.size() + " Hay esta cantidad de pokemones");
        if (first == true || distance > 100) {
            if (first == true) {
                locinit = location;
                distance = getDistance(locinit.getLatitude(), locinit.getLongitude(), location.getLatitude(), location.getLongitude());
                System.out.println("Aquí en first true");
                first = false;
            } else {
                if (locinit.distanceTo(location) > 100) {
                    try {
                        System.out.println("Aquí en el mayor a 100 que carajos pasa");
                        ubicacion = "http://190.144.171.172/function3.php?lat=" + location.getLatitude() + "&lng=" + location.getLongitude();
                        UpdateMap(ubicacion);
                        locinit = location;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            Log.d(TAG, "" + first);
            Log.d(TAG, "" + locinit.toString());


            for (int i = 0; i < markers.size(); i++) {
                int random = new Random().nextInt(16);
                Log.d(TAG, pokes.get(i).getName());
                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(markers.get(i).getLat()), Double.parseDouble(markers.get(i).getLn()))).icon(BitmapDescriptorFactory.fromBitmap(pokes.get(random).getBitmap())).title(random + ""));
            }
        }

        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }

        if (mMap != null) {
            currLocationMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Here I'm"));
        }

    }

    public boolean VerificarRed() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                LocationServices.FusedLocationApi.requestLocationUpdates(googleapiclient, locationRequest, locationListener);
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk = (float) (180.f / Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = cos(a1) * cos(a2) * cos(b1) * cos(b2);
        double t2 = cos(a1) * Math.sin(a2) * cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = acos(t1 + t2 + t3);
        Log.d(String.valueOf(6366000 * tt), TAG);

        return 6366000 * tt;
    }

    public double greatCircleInKilometers(double lat1, double long1, double lat2, double long2) {
        double PI_RAD = Math.PI / 180.0;
        double phi1 = lat1 * PI_RAD;
        double phi2 = lat2 * PI_RAD;
        double lam1 = long1 * PI_RAD;
        double lam2 = long2 * PI_RAD;

        return 6371.01 * acos(Math.sin(phi1) * Math.sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
    }

    public double greatCircleInMeters(LatLng latLng1, LatLng latLng2) {
        return greatCircleInKilometers(latLng1.latitude, latLng1.longitude, latLng2.latitude,
                latLng2.longitude) * 1000;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}

