import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorPageComponentComponent } from './error-page-component.component';

describe('ErrorPageComponentComponent', () => {
  let component: ErrorPageComponentComponent;
  let fixture: ComponentFixture<ErrorPageComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ErrorPageComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ErrorPageComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
