def add_rolling_avg(df, window=2):
    df["rolling_avg"] = (
        df.groupby("sensor_id")["value"]
          .rolling(window)
          .mean()
          .reset_index(level=0, drop=True)
    )
    return df
