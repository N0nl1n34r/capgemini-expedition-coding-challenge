"""
Read the TVOC and CO2 values from the SparkFun CSS811 breakout board

A new sensor requires at 48-burn in. Once burned in a sensor requires
20 minutes of run in before readings are considered good.
"""
import sys
import time
import socket

import ccs811LIBRARY

sensor = ccs811LIBRARY.CCS811()


def setup(mode=1):
    print('Starting CCS811 Read')
    sensor.configure_ccs811()
    sensor.set_drive_mode(mode)

    if sensor.check_for_error():
        sensor.print_error()
        raise ValueError('Error at setDriveMode.')

    result = sensor.get_base_line()
    sys.stdout.write("baseline for this sensor: 0x")
    if result < 0x100:
        sys.stdout.write('0')
    if result < 0x10:
        sys.stdout.write('0')
    sys.stdout.write(str(result) + "\n")


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(("192.168.0.7", 8080))
s.send(bytes([0x12]))  # identify ourselves as following the protocol

setup(1)  # Setting mode

while True:
    # s.send(bytes([0x10]) + (400).to_bytes(4, 'big'))
    if sensor.data_available():
        sensor.read_logorithm_results()
        curCo2 = sensor.CO2
        curTvoc = sensor.tVOC
        s.send(bytes([0x10] + curCo2.to_bytes(4, 'big')))
        s.send(bytes([0x20] + curTvoc.to_bytes(4, 'big')))
    elif sensor.check_for_error():
        sensor.print_error()
    time.sleep(1)
