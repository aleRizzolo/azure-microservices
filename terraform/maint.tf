terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "3.53.0"
    }
  }
}

provider "azurerm" {
  features {}
}

resource "azurerm_resource_group" "esame" {
  name     = "esame-cloud"
  location = "East US"
}