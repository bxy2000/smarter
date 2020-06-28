export interface IResponseMessage {
  success?: boolean
  message?: string,
}

export class ResponseMessage implements IResponseMessage {
  constructor(
    public success?: boolean,
    public message?: string
  ) {
  }
}
