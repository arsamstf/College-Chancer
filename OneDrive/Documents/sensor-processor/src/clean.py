import pandas as pd

def clean_data(df):
    df["value"] = pd.to_numeric(df["value"], errors="coerce")
    df = df.dropna(subset=["timestamp", "sensor_id", "value"])
    df = df.sort_values(["sensor_id", "timestamp"]).reset_index(drop=True)
    return df
