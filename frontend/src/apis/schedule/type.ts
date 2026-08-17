/** 任务日志类型 */
export interface JobLogResp {
  id: number
  groupName: string
  jobName: string
  jobId: number
  taskBatchStatus: number
  operationReason: number
  executorType: number
  executorInfo: string
  executionAt: string
  createDt: string
}
export interface JobLogQuery {
  jobId?: number
  groupName?: string
  jobName?: string
  taskBatchStatus?: number
  datetimeRange?: Array<string>
}
export interface JobLogPageQuery extends JobLogQuery, PageQuery {}
