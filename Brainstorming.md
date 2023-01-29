
## High-level class architecture

Thoughts:
- KrpcjMain runs the App
- The App spawns:
  - The InstrumentPanel UI
  - The CLI system
  - The KRPC connection (driver?)

Data flow:
- Fetch data using KRPC
- Assemble telemetry "packet" (snapshot?)
- Examine packet for events/warnings
- Use packet to update UI
  - Alert on events/warnings in the UI
- Telemetry snapshots are saved and referencable (singleton?)

KRPC flight data: assemble packet, which is basically UI state
- Flight data recorder (array?)
  - Altimeter: display altitude
  - VSI: display vertical speed
  - GPWS: read conditions and alert
- Save current snapshot in list of snapshots
