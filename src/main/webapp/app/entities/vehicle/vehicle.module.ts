import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { VehicleComponent } from './vehicle.component';
import { VehicleDetailComponent } from './vehicle-detail.component';
import { VehicleUpdateComponent } from './vehicle-update.component';
import { VehicleDeleteDialogComponent } from './vehicle-delete-dialog.component';
import { vehicleRoute } from './vehicle.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(vehicleRoute)],
  declarations: [VehicleComponent, VehicleDetailComponent, VehicleUpdateComponent, VehicleDeleteDialogComponent],
  entryComponents: [VehicleDeleteDialogComponent],
})
export class MgmtBaseNautiqueVehicleModule {}
