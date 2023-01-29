package krpcj.fsw.driver;

import java.io.IOException;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.services.KRPC;
import krpc.client.services.SpaceCenter;
import krpc.client.services.SpaceCenter.Flight;
import krpc.client.services.SpaceCenter.ReferenceFrame;
import krpc.client.services.SpaceCenter.Vessel;
import krpcj.fsw.config.Constants;
import krpcj.fsw.exception.DriverException;
import krpcj.fsw.exception.ValidationException;

/**
 * Provide a layer of abstraction on top of KRPC, such that no other classes
 * should be concerned with how KRPC works.
 * 
 * This should also probably be a singleton so it can be used anywhere.
 */
public class KrpcDriver {

    private static final Logger logger = LoggerFactory.getLogger(KrpcDriver.class.getSimpleName());

    // KRPC
    private Connection conn;
    private KRPC krpc;
    private SpaceCenter spaceCenter;
    private Vessel vessel;

    private Flight flightInfoVessel;
    private ReferenceFrame refFrameVessel;

    private Flight flightInfoSurf;
    private ReferenceFrame refFrameSurf;

    // Default constructor
    public KrpcDriver() {

    }

    // Test constructor
    public KrpcDriver(Connection testConn, KRPC testKRPC, SpaceCenter testSc, Vessel testVessel) {
        this.conn = testConn;
        this.krpc = testKRPC;
        this.spaceCenter = testSc;
        this.vessel = testVessel;
    }

    // region: KRP connection funcs ********************************************

    public void initDriver() {
        this.connect();
        this.initializeFlightInfo();
    }

    public void connect() throws DriverException {
        // Do not overwrite or lose pre-existing connection
        if (conn != null) {
            throw new ValidationException("Driver cannot connect because an active connection is already established.");
        }

        // Establish a new connection
        try {
            this.conn = Connection.newInstance(Constants.CLIENT_NAME, Constants.SERVER_IP, Constants.SERVER_RPC_PORT, Constants.SERVER_STREAM_PORT);
            this.krpc = KRPC.newInstance(conn);
            logger.info("Driver successfully connected to KRPC! Version: " + krpc.getStatus().getVersion());
        } catch (IOException | RPCException e) {
            throw new DriverException("Driver failed to connect to KRPC instance: " + e.getMessage(), e);
        }
    }

    public void disconnect() throws DriverException {
        try {
            this.conn.close();
            this.conn = null;
        } catch (IOException e) {
            throw new DriverException("Driver failed to disconnect: " + e.getMessage(), e);
        }
    }

    protected void initializeFlightInfo() throws DriverException {
        // Init Space Center
        if (conn == null) {
            throw new DriverException("Driver cannot initialize flight info without an active connection.");
        }
        this.spaceCenter = SpaceCenter.newInstance(this.conn);

        // Init vessel
        try {
            this.vessel = this.spaceCenter.getActiveVessel();
            logger.info("Connected to vessel named " + this.vessel.getName());
        } catch (RPCException e) {
            throw new DriverException("Driver failed to get the active vessel: " + e.getMessage(), e);
        }

        // Init flight info (both surface and vessel)
        try {
            this.refFrameVessel = this.vessel.getReferenceFrame();
            this.refFrameSurf = this.vessel.getSurfaceReferenceFrame();
        } catch (RPCException e) {
            throw new DriverException("Driver failed to read vessel reference frames: " + e.getMessage(), e);
        }
    }

    // endregion ***************************************************************

    // region: Flight data streams *********************************************
    protected void initalizeDataStreams() {
        // Position data
        // Orientation data
        // Motion data
        // Forces
    }

    // endregion ***************************************************************

    // region: Position data ***************************************************

    public Double getASL() throws DriverException {
        try {
            return this.flightInfoSurf.getMeanAltitude();
        } catch (RPCException e) {
            throw new DriverException("Driver failed to read ASL: " + e.getMessage(), e);
        }
    }

    public Double getAGL() throws DriverException {
        try {
            return this.flightInfoSurf.getSurfaceAltitude();
        } catch (RPCException e) {
            throw new DriverException("Driver failed to read AGL: " + e.getMessage(), e);
        }
    }

    public Pair<Double, Double> getGpsCoords() throws DriverException {
        try {
            double lat = this.flightInfoSurf.getLatitude();
            double lon = this.flightInfoSurf.getLongitude();
            return Pair.with(lat, lon);
        } catch (RPCException e) {
            throw new DriverException("Driver failed to read GPS coords: " + e.getMessage(), e);
        }
    }

    // endregion ***************************************************************

    // region: Orientation data ************************************************

    public Double getPitch() throws DriverException {
        try {
            return Double.valueOf(this.flightInfoSurf.getPitch());
        } catch (RPCException e) {
            throw new DriverException("Driver failed to read pitch: " + e.getMessage(), e);
        }
    }

    public Double getRoll() throws DriverException {
        try {
            return Double.valueOf(this.flightInfoSurf.getRoll());
        } catch (RPCException e) {
            throw new DriverException("Driver failed to read roll: " + e.getMessage(), e);
        }
    }

    public Double getHeading() throws DriverException {
        try {
            return Double.valueOf(this.flightInfoSurf.getHeading());
        } catch (RPCException e) {
            throw new DriverException("Driver failed to read heading: " + e.getMessage(), e);
        }
    }

    // endregion ***************************************************************

    // region: Motion data *****************************************************

    // endregion ***************************************************************
}
