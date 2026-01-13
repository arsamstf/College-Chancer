from src.load import load_data
from src.clean import clean_data
from src.analyze import add_rolling_avg
from src.detect import detect_anomalies
from src.plot import plot_sensor

RAW = "data/sensors_raw.csv"

def main():
    df = load_data(RAW)
    df = clean_data(df)
    df = add_rolling_avg(df)

    out_of_range, spikes = detect_anomalies(df)

    df.to_csv("outputs/cleaned.csv", index=False)
    out_of_range.to_csv("outputs/out_of_range.csv", index=False)
    spikes.to_csv("outputs/spikes.csv", index=False)
    plot_sensor(df, "temp_1")

    print("Processing complete")
    print(f"Rows processed: {len(df)}")
    print(f"Out-of-range alerts: {len(out_of_range)}")
    print(f"Spike alerts: {len(spikes)}")

if __name__ == "__main__":
    main()
