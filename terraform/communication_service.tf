resource "azurerm_communication_service" "communication-esame" {
  name                = "communication-esame-cloud"
  resource_group_name = azurerm_resource_group.esame.name
  data_location       = "United States"
}