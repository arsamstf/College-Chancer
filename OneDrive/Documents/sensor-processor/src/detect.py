def detect_anomalies(df):
    out_of_range = df[(df["value"] < 0) | (df["value"] > 100)]
    df["delta"] = df.groupby("sensor_id")["value"].diff().abs()
    spikes = df[df["delta"] > 30]

    return out_of_range, spikes
