import * as dotenv from "dotenv"
import { EmailClient } from "@azure/communication-email"

dotenv.config()

const connectionString = process.env.COMMUNICATION_SERVICES_CONNECTION_STRING
const emailClient = new EmailClient(connectionString!)

export const sendEmail = async (email: string) => {
  const POLLER_WAIT_TIME = 10
  try {
    const message = {
      senderAddress: "esamecloud2023",
      content: {
        subject: "Welcome to Azure Communication Services Email",
        plainText: "This email message is sent from Azure Communication Services Email using the JavaScript SDK.",
      },
      recipients: {
        to: [
          {
            address: email,
          },
        ],
      },
    }

    const poller = await emailClient.beginSend(message)

    if (!poller.getOperationState().isStarted) {
      throw "Poller was not started."
    }

    let timeElapsed = 0
    while (!poller.isDone()) {
      poller.poll()
      console.log("Email send polling in progress")

      await new Promise((resolve) => setTimeout(resolve, POLLER_WAIT_TIME * 1000))
      timeElapsed += 10

      if (timeElapsed > 18 * POLLER_WAIT_TIME) {
        throw "Polling timed out."
      }
    }
  } catch (e) {
    console.log(e)
  }
}
