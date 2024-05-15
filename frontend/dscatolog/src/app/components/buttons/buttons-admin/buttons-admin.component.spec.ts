import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonsAdminComponent } from './buttons-admin.component';

describe('ButtonsAdminComponent', () => {
  let component: ButtonsAdminComponent;
  let fixture: ComponentFixture<ButtonsAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonsAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
