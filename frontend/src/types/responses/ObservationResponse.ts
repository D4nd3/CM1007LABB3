import { ConditionResponse } from '../responses';

export interface ObservationResponse {
  id: number;
  description: string;
  created: Date;
  visitDate: Date;
  conditions: ConditionResponse[];
}