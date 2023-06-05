resource "azurerm_servicebus_namespace" "namespace-esame" {
  name                = var.service_bus_namespace_name
  location            = azurerm_resource_group.esame.location
  resource_group_name = azurerm_resource_group.esame.name
  sku                 = "Basic"
}

resource "azurerm_servicebus_queue" "coda-esame" {
  name         = var.service_bus_queue_name
  namespace_id = azurerm_servicebus_namespace.namespace-esame.id
}