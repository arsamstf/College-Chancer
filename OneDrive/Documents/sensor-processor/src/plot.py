import matplotlib.pyplot as plt

def plot_sensor(df, sensor_id):
    sdf = df[df["sensor_id"] == sensor_id]

    plt.figure()
    plt.plot(sdf["timestamp"], sdf["value"], label="value")
    plt.plot(sdf["timestamp"], sdf["rolling_avg"], label="rolling avg")

    plt.title(f"Sensor: {sensor_id}")
    plt.xlabel("Time")
    plt.ylabel("Value")
    plt.legend()
    plt.tight_layout()
    plt.show()
