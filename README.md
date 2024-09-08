# Priest Booking App

The **Priest Booking App** simplifies the process of booking priests for various services and ordering required items from merchants. The app supports three different user roles: **customer**, **priest**, and **merchant**, each with distinct functionality.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Project Overview

This app provides an easy-to-use interface for customers to book priests for religious services and order necessary items from merchants who supply goods for the rituals. When a customer books a service, the selected priest is notified, and when the customer orders items, the merchant receives a notification to prepare the necessary items.

### Roles:
- **Customer**: Can view and select services, book priests, and order items from merchants.
- **Priest**: Receives notifications when a service is booked and can manage appointments.
- **Merchant**: Receives notifications when a customer orders ritual-related items and can process orders.

## Features
- **Multi-login System**: Separate logins for customers, priests, and merchants.
- **Service Selection**: Customers can view and select services offered by priests.
- **Order Items**: Customers can order required items from merchants for their booked service.
- **Notifications**: Both priests and merchants get notified about bookings and orders.
- **Real-time Updates**: Priests and merchants are informed in real-time about customer actions.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/priest-booking-app.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files by clicking File > Sync Project with Gradle Files.
4. Make sure you have all necessary dependencies installed (e.g., SDK, libraries).

## Usage

1. Open the project in Android Studio.
2. Select the target role (customer, priest, merchant) by logging in with the respective credentials.
3. Customers can browse services, select a service, and place orders for required items.
4. Priests will receive notifications of service bookings, while merchants will be notified of item orders.
5. Both priests and merchants can manage their appointments and orders accordingly.

## Prerequisites

- Android SDK 30 or higher
- Java 8
- Internet connection for real-time updates
