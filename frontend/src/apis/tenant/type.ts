/** 租户套餐 */
export interface TenantPackageResp {
  id: string
  name: string
  sort: number
  menuCheckStrictly: string
  description: string
  status: string
  menuIds: []
  createUser: string
  createTime: string
  updateUser: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface TenantPackageQuery {
  description?: string
  status?: string
  sort: Array<string>
}
export interface TenantPackagePageQuery extends TenantPackageQuery, PageQuery {}
