resource "azurerm_resource_group" "esame-cloud" {
  name     = "esame-cloud"
  location = "eastus"
}

resource "azurerm_servicebus_namespace" "namespace-esame" {
  name                = "esamecloud-servicebus-namespace"
  location            = azurerm_resource_group.esame-cloud.location
  resource_group_name = azurerm_resource_group.esame-cloud.name
  sku                 = "Basic"

}

resource "azurerm_servicebus_queue" "queue" {
  name         = "esame-cloud"
  namespace_id = azurerm_servicebus_namespace.namespace-esame.id

}